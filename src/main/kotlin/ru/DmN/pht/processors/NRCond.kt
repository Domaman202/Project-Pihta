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
        processor.calc(processor.computeList(processor.computeList(node.nodes[0], ctx)[0], ctx)[0], ctx)

    override fun process(node: NodeNodesList, processor: Processor, ctx: ProcessingContext, valMode: Boolean): Node {
        val iter = processor.computeList(node.nodes[0], ctx).map { processor.computeList(it, ctx).toMutableList() }.iterator()
        val info = node.info
        val first = nodeIf(info, iter, processor, ctx, valMode)
        var last = first
        while (iter.hasNext()) {
            val expr = nodeIf(info, iter, processor, ctx, valMode)
            last.nodes += expr
            last = expr
        }
        return first
    }

    private fun nodeIf(info: INodeInfo, iter: Iterator<MutableList<Node>>, processor: Processor, ctx: ProcessingContext, valMode: Boolean) =
        processor.process(nodeIf(info, iter.next()), ctx, valMode) as NodeNodesList
}