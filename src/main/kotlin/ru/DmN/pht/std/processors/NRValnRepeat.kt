package ru.DmN.pht.std.processors

import ru.DmN.pht.base.Processor
import ru.DmN.pht.base.ast.Node
import ru.DmN.pht.base.ast.NodeNodesList
import ru.DmN.pht.base.processor.utils.ProcessingContext
import ru.DmN.pht.base.processor.utils.ValType
import ru.DmN.pht.base.processors.NRDefault
import ru.DmN.pht.base.utils.VirtualType
import ru.DmN.pht.std.processor.utils.nodeProgn
import ru.DmN.pht.std.utils.computeInt

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