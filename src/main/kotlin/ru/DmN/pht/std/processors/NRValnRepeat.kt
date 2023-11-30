package ru.DmN.pht.std.processors

import ru.DmN.pht.std.utils.computeInt
import ru.DmN.siberia.Processor
import ru.DmN.siberia.ast.Node
import ru.DmN.siberia.ast.NodeNodesList
import ru.DmN.siberia.processor.ctx.ProcessingContext
import ru.DmN.siberia.processor.utils.ValType
import ru.DmN.siberia.processor.utils.nodeProgn
import ru.DmN.siberia.processors.NRDefault
import ru.DmN.siberia.utils.VirtualType

object NRValnRepeat : IStdNodeProcessor<NodeNodesList> {
    override fun calc(node: NodeNodesList, processor: Processor, ctx: ProcessingContext): VirtualType? =
        processor.calc(node.nodes.last(), ctx)

    override fun process(node: NodeNodesList, processor: Processor, ctx: ProcessingContext, mode: ValType): NodeNodesList =
        NRDefault.process(nodeProgn(node.token.line, repeat(node, processor, ctx)), processor, ctx, mode)

    override fun computeList(node: NodeNodesList, processor: Processor, ctx: ProcessingContext): List<Node> =
        repeat(node, processor, ctx)

    private fun repeat(node: NodeNodesList, processor: Processor, ctx: ProcessingContext): MutableList<Node> {
        val nodes = node.nodes.drop(1)
        val list = ArrayList<Node>()
        for (i in 0 until processor.computeInt(node.nodes[0], ctx))
            list += nodes
        return list
    }
}