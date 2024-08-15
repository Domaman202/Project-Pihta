package ru.DmN.pht.processors

import ru.DmN.pht.ast.NodeMCall
import ru.DmN.pht.ast.NodeMCall.Type.STATIC
import ru.DmN.pht.ast.NodeMath
import ru.DmN.pht.processor.ctx.global
import ru.DmN.pht.processor.utils.*
import ru.DmN.pht.utils.node.*
import ru.DmN.pht.utils.node.NodeParsedTypes.SHIFT_LEFT
import ru.DmN.pht.utils.node.NodeParsedTypes.SHIFT_RIGHT
import ru.DmN.pht.utils.node.NodeTypes.MCALL_
import ru.DmN.pht.utils.text
import ru.DmN.pht.utils.vtype.VTDynamic
import ru.DmN.siberia.ast.Node
import ru.DmN.siberia.ast.NodeNodesList
import ru.DmN.siberia.processor.Processor
import ru.DmN.siberia.processor.ctx.ProcessingContext
import ru.DmN.siberia.processors.INodeProcessor
import ru.DmN.siberia.utils.mapMutable
import ru.DmN.siberia.utils.node.INodeInfo
import ru.DmN.siberia.utils.vtype.VirtualMethod
import ru.DmN.siberia.utils.vtype.VirtualType
import kotlin.math.min

object NRMath : INodeProcessor<NodeNodesList> {
    override fun calc(node: NodeNodesList, processor: Processor, ctx: ProcessingContext): VirtualType {
        val firstType = processor.calc(node.nodes[0], ctx)!!
        return findExtend(firstType, node.text, node.nodes.drop(1), processor, ctx)?.first?.rettype ?: firstType
    }

    override fun process(node: NodeNodesList, processor: Processor, ctx: ProcessingContext, valMode: Boolean): Node? {
        val info = node.info
        val nodes = processor.processNodes(node, ctx, true)
        val firstType = processor.calc(nodes[0], ctx)!!
        if (firstType == VTDynamic) {
            val values =
                mutableListOf<Node>(
                    nodeValue(info, info.type.toString()),
                    nodeArrayOfType(info, "dynamic", nodes)
                )
            val result =
                NRMCall.findMethod(
                    ctx.global.getType("ru.DmN.pht.utils.DynamicUtils"),
                    "math",
                    values,
                    Static.ANY,
                    processor,
                    ctx
                )
            return NodeMCall(
                info.withType(MCALL_),
                NRMCall.processArguments(
                    info,
                    processor,
                    ctx,
                    result.method,
                    values,
                    result.compression
                ),
                null,
                nodeValueClass(info, result.method.declaringClass.name),
                result.method,
                STATIC
            )
        }
        val result = getExtend(firstType, node.text, nodes.drop(1), processor, ctx)
        return if (result == null)
            if (valMode)
                NodeMath(
                    info.processed,
                    when (info.type) {
                        SHIFT_LEFT,
                        SHIFT_RIGHT -> nodes
                        else -> nodes.mapMutable { processor.process(nodeAs(info, it, firstType.name), ctx, true)!! }
                    },
                    firstType
                )
            else null
        else NodeMCall(
            info.withType(MCALL_),
            processArguments(info, processor, ctx, listOf(nodes[0]) + result.args, result),
            null,
            nodeValueClass(info, result.method.declaringClass.name),
            result.method,
            NodeMCall.Type.EXTEND
        )
    }

    fun processArguments(info: INodeInfo, processor: Processor, ctx: ProcessingContext, args: List<Node>, resultB: MethodFindResultB): MutableList<Node> =
        NRMCall.processArguments(info, processor, ctx, resultB.method, args, resultB.compression || resultB.method.modifiers.varargs)

    fun getExtend(type: VirtualType, name: String, args: List<Node>, processor: Processor, ctx: ProcessingContext): MethodFindResultB? {
        val result = findExtend(type, name, args, processor, ctx) ?: return null
        val adapted = ArrayList<Node>()
        val j = min(args.size, result.first.argsc.size)
        for (i in 0 until j)
            adapted += processor.adaptToType(args[i], result.first.argsc[i], ctx)
        for (i in j until args.size)
            adapted += args[i]
        return MethodFindResultB(adapted, result.first, result.second)
    }

    fun findExtend(type: VirtualType, name: String, args: List<Node>, processor: Processor, ctx: ProcessingContext): Pair<VirtualMethod, Boolean>? =
        ctx.global.getMethodVariants(type, name, args.map { ICastable.of(it, processor, ctx) }).firstOrNull()
}