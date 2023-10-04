package ru.DmN.pht.std.util.processors

import ru.DmN.pht.base.Processor
import ru.DmN.pht.base.parser.ast.Node
import ru.DmN.pht.base.parser.ast.NodeNodesList
import ru.DmN.pht.base.processor.utils.ProcessingContext
import ru.DmN.pht.base.processor.utils.ValType
import ru.DmN.pht.std.base.processor.processors.IStdNodeProcessor
import ru.DmN.pht.std.base.processor.utils.nodePrognOf
import ru.DmN.pht.std.base.utils.computeInt

object NRValnRepeat : IStdNodeProcessor<NodeNodesList> { // todo: calc?
    override fun process(node: NodeNodesList, processor: Processor, ctx: ProcessingContext, mode: ValType): NodeNodesList =
        nodePrognOf(node.tkOperation.line, repeat(node, processor, ctx))

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