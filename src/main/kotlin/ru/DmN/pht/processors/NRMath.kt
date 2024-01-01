package ru.DmN.pht.std.processors

import ru.DmN.pht.std.ast.NodeMCall
import ru.DmN.pht.std.node.NodeParsedTypes
import ru.DmN.pht.std.node.NodeTypes
import ru.DmN.pht.std.node.processed
import ru.DmN.pht.std.processor.utils.ICastable
import ru.DmN.pht.std.processor.utils.global
import ru.DmN.pht.std.processor.utils.nodeAs
import ru.DmN.pht.std.processor.utils.nodeValueClass
import ru.DmN.pht.std.utils.adaptToType
import ru.DmN.pht.std.utils.processNodes
import ru.DmN.pht.std.utils.text
import ru.DmN.siberia.Processor
import ru.DmN.siberia.ast.Node
import ru.DmN.siberia.ast.NodeNodesList
import ru.DmN.siberia.processor.ctx.ProcessingContext
import ru.DmN.siberia.processor.utils.ValType
import ru.DmN.siberia.processors.INodeProcessor
import ru.DmN.siberia.utils.VirtualMethod
import ru.DmN.siberia.utils.VirtualType

object NRMath : INodeProcessor<NodeNodesList> {
    override fun calc(node: NodeNodesList, processor: Processor, ctx: ProcessingContext): VirtualType {
        val firstType = processor.calc(node.nodes[0], ctx)!!
        return findExtend(firstType, node.text, node.nodes.drop(1), processor, ctx)?.rettype ?: firstType
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
                        else -> nodes.map { NRAs.process(nodeAs(info, it, firstType.name), processor, ctx, ValType.VALUE)!! }
                    }).toMutableList()
                )
            else null
        else NodeMCall(
            info.withType(NodeTypes.MCALL_),
            NRMCall.processArguments(info, processor, ctx, result.second, listOf(nodes[0]) + result.first),
            null,
            nodeValueClass(info, result.second.declaringClass!!.name),
            result.second,
            NodeMCall.Type.EXTEND
        )
    }

    fun getExtend(type: VirtualType, name: String, args: List<Node>, processor: Processor, ctx: ProcessingContext): Pair<List<Node>, VirtualMethod>? {
        val method = findExtend(type, name, args, processor, ctx) ?: return null
        return Pair(args.mapIndexed { i, it -> processor.adaptToType(method.argsc[i], it, ctx) }.toList(), method)
    }

    fun findExtend(type: VirtualType, name: String, args: List<Node>, processor: Processor, ctx: ProcessingContext): VirtualMethod? =
        ctx.global.getMethodVariants(type, name, args.map { ICastable.of(it, processor, ctx) }.toList()).firstOrNull()
}