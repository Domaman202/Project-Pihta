package ru.DmN.pht.std.processors

import ru.DmN.pht.base.Processor
import ru.DmN.pht.base.ast.Node
import ru.DmN.pht.base.ast.NodeNodesList
import ru.DmN.pht.base.processor.ProcessingContext
import ru.DmN.pht.base.processor.ValType
import ru.DmN.pht.std.processor.utils.nodeProgn
import ru.DmN.pht.std.utils.computeInt

object NRValnRepeat : ru.DmN.pht.std.processors.IStdNodeProcessor<NodeNodesList> { // todo: calc?
    override fun process(node: NodeNodesList, processor: Processor, ctx: ProcessingContext, mode: ValType): NodeNodesList =
        nodeProgn(node.token.line, repeat(node, processor, ctx))

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