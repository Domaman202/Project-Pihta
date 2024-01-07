package ru.DmN.pht.std.processors

import ru.DmN.pht.std.node.nodeIf
import ru.DmN.pht.std.utils.computeList
import ru.DmN.siberia.Processor
import ru.DmN.siberia.ast.Node
import ru.DmN.siberia.ast.NodeNodesList
import ru.DmN.siberia.node.INodeInfo
import ru.DmN.siberia.processor.ctx.ProcessingContext
import ru.DmN.siberia.processor.utils.ValType
import ru.DmN.siberia.processors.INodeProcessor
import ru.DmN.siberia.utils.VirtualType

object NRCond : INodeProcessor<NodeNodesList> {
    override fun calc(node: NodeNodesList, processor: Processor, ctx: ProcessingContext): VirtualType? =
        processor.calc(processor.computeList(processor.computeList(node.nodes[0], ctx)[0], ctx)[0], ctx)

    override fun process(node: NodeNodesList, processor: Processor, ctx: ProcessingContext, mode: ValType): Node {
        val iter = processor.computeList(node.nodes[0], ctx).map { processor.computeList(it, ctx).toMutableList() }.iterator()
        val info = node.info
        val first = nodeIf(info, iter, processor, ctx, mode)
        var last = first
        while (iter.hasNext()) {
            val expr = nodeIf(info, iter, processor, ctx, mode)
            last.nodes += expr
            last = expr
        }
        return first
    }

    private fun nodeIf(info: INodeInfo, iter: Iterator<MutableList<Node>>, processor: Processor, ctx: ProcessingContext, mode: ValType) =
        processor.process(nodeIf(info, iter.next()), ctx, mode) as NodeNodesList
}