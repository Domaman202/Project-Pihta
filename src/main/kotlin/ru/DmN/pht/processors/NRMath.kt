package ru.DmN.pht.std.processors

import ru.DmN.pht.processor.utils.MethodFindResultB
import ru.DmN.pht.std.ast.NodeMCall
import ru.DmN.pht.std.node.*
import ru.DmN.pht.std.processor.utils.ICastable
import ru.DmN.pht.std.processor.utils.global
import ru.DmN.pht.std.utils.adaptToType
import ru.DmN.pht.std.utils.processNodes
import ru.DmN.pht.std.utils.text
import ru.DmN.siberia.Processor
import ru.DmN.siberia.ast.Node
import ru.DmN.siberia.ast.NodeNodesList
import ru.DmN.siberia.node.INodeInfo
import ru.DmN.siberia.processor.ctx.ProcessingContext
import ru.DmN.siberia.processor.utils.ValType
import ru.DmN.siberia.processors.INodeProcessor
import ru.DmN.siberia.utils.VirtualMethod
import ru.DmN.siberia.utils.VirtualType

object NRMath : INodeProcessor<NodeNodesList> {
    override fun calc(node: NodeNodesList, processor: Processor, ctx: ProcessingContext): VirtualType {
        val firstType = processor.calc(node.nodes[0], ctx)!!
        return findExtend(firstType, node.text, node.nodes.drop(1), processor, ctx)?.first?.rettype ?: firstType
    }

    override fun process(node: NodeNodesList, processor: Processor, ctx: ProcessingContext, mode: ValType): Node? {
        val nodes = processor.processNodes(node, ctx, ValType.VALUE)
        val firstType = processor.calc(nodes[0], ctx)!!
        val result = getExtend(firstType, node.text, nodes.drop(1), processor, ctx)
        val info = node.info
        return if (result == null)
            if (mode == ValType.VALUE)
                NodeNodesList(
                    info.processed,
                    (when (info.type) {
                        NodeParsedTypes.SHIFT_LEFT,
                        NodeParsedTypes.SHIFT_RIGHT -> nodes

                        else -> nodes.map {
                            NRAs.process(
                                nodeAs(info, it, firstType.name),
                                processor,
                                ctx,
                                ValType.VALUE
                            )!!
                        }
                    }).toMutableList()
                )
            else null
        else NodeMCall(
            info.withType(NodeTypes.MCALL_),
            processArguments(info, processor, ctx, listOf(nodes[0]) + result.args, result),
            null,
            nodeValueClass(info, result.method.declaringClass!!.name),
            result.method,
            NodeMCall.Type.EXTEND
        )
    }

    fun processArguments(info: INodeInfo, processor: Processor, ctx: ProcessingContext, args: List<Node>, resultB: MethodFindResultB): MutableList<Node> =
        NRMCall.processArguments(info, processor, ctx, resultB.method, args, resultB.compression || resultB.method.modifiers.varargs)

    fun getExtend(type: VirtualType, name: String, args: List<Node>, processor: Processor, ctx: ProcessingContext): MethodFindResultB? {
        val result = findExtend(type, name, args, processor, ctx) ?: return null
        return MethodFindResultB(args.mapIndexed { i, it -> processor.adaptToType(result.first.argsc[i], it, ctx) }.toList(), result.first, result.second)
    }

    fun findExtend(type: VirtualType, name: String, args: List<Node>, processor: Processor, ctx: ProcessingContext): Pair<VirtualMethod, Boolean>? =
        ctx.global.getMethodVariants(type, name, args.map { ICastable.of(it, processor, ctx) }.toList()).firstOrNull()
}