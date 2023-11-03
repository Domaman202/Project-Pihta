package ru.DmN.pht.std.processors

import ru.DmN.pht.base.Processor
import ru.DmN.pht.base.ast.Node
import ru.DmN.pht.base.ast.NodeNodesList
import ru.DmN.pht.base.processor.ProcessingContext
import ru.DmN.pht.base.processor.ValType
import ru.DmN.pht.base.processors.INodeProcessor
import ru.DmN.pht.base.utils.VirtualType
import ru.DmN.pht.std.processor.utils.nodeAs
import ru.DmN.pht.std.processor.utils.nodeMCall
import ru.DmN.pht.std.utils.line
import ru.DmN.pht.std.utils.processNodes

object NRMath : INodeProcessor<NodeNodesList> {
    override fun calc(node: NodeNodesList, processor: Processor, ctx: ProcessingContext): VirtualType {
        val firstType = processor.calc(node.nodes[0], ctx)
        return if (firstType!!.isPrimitive)
            firstType // todo: primitive extensions
        else NRMCall.calc(
            nodeMCall(
                node.token.line,
                node.nodes[0],
                node.token.text!!,
                node.nodes.drop(1)
            ), processor, ctx
        )
    }

    override fun process(node: NodeNodesList, processor: Processor, ctx: ProcessingContext, mode: ValType): Node? {
        val nodes = processor.processNodes(node, ctx, ValType.VALUE)
        val firstType = processor.calc(nodes[0], ctx)
        return if (firstType!!.isPrimitive)
            if (mode == ValType.VALUE) { // todo: primitive extensions
                val line = node.line
                NodeNodesList(
                    node.token.processed(),
                    nodes.map { NRAs.process(nodeAs(line, it, firstType.name), processor, ctx, ValType.VALUE)!! }.toMutableList(),
                )
            } else null
        else NRMCall.process(
            nodeMCall(
                node.line,
                nodes[0],
                node.token.text!!,
                nodes.drop(1)
            ), processor, ctx, ValType.VALUE
        )
    }
}