package ru.DmN.pht.processors

import ru.DmN.pht.processor.utils.computeList
import ru.DmN.pht.utils.node.nodeIf
import ru.DmN.siberia.ast.Node
import ru.DmN.siberia.ast.NodeNodesList
import ru.DmN.siberia.processor.Processor
import ru.DmN.siberia.processor.ctx.ProcessingContext
import ru.DmN.siberia.processors.INodeProcessor
import ru.DmN.siberia.utils.node.INodeInfo
import ru.DmN.siberia.utils.vtype.VirtualType

object NRCond : INodeProcessor<NodeNodesList> {
    override fun calc(node: NodeNodesList, processor: Processor, ctx: ProcessingContext): VirtualType? =
        (processor.computeList(node.nodes[0], ctx).firstOrNull()?.let { processor.computeList(it, ctx)[0] } ?: node.nodes.lastOrNull())?.let { processor.calc(it, ctx) }

    override fun process(node: NodeNodesList, processor: Processor, ctx: ProcessingContext, valMode: Boolean): Node? {
        val iter = processor.computeList(node.nodes[0], ctx).map { processor.computeList(it, ctx).toMutableList() }.iterator()
        if (!iter.hasNext())
            return node.nodes.lastOrNull()?.let { processor.process(it, ctx, valMode) }
        val info = node.info
        val first = nodeIf(info, iter, processor, ctx, valMode)
        var last = first
        while (iter.hasNext()) {
            val expr = nodeIf(info, iter, processor, ctx, valMode)
            last.nodes += expr
            last = expr
        }
        node.nodes.lastOrNull()?.let { last.nodes += it }
        return first
    }

    private fun nodeIf(info: INodeInfo, iter: Iterator<MutableList<Node>>, processor: Processor, ctx: ProcessingContext, valMode: Boolean): NodeNodesList =
        processor.process(nodeIf(info, iter.next()), ctx, valMode) as NodeNodesList
}