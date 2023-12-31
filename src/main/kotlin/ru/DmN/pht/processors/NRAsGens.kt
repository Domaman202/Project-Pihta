package ru.DmN.pht.std.processors

import ru.DmN.pht.std.node.nodeAs
import ru.DmN.pht.std.node.nodeWithGens
import ru.DmN.siberia.Processor
import ru.DmN.siberia.ast.Node
import ru.DmN.siberia.ast.NodeNodesList
import ru.DmN.siberia.processor.ctx.ProcessingContext
import ru.DmN.siberia.processor.utils.ValType
import ru.DmN.siberia.processors.INodeProcessor
import ru.DmN.siberia.utils.VirtualType

object NRAsGens : INodeProcessor<NodeNodesList> {
    override fun calc(node: NodeNodesList, processor: Processor, ctx: ProcessingContext): VirtualType? =
        processor.calc(create(node), ctx)

    override fun process(node: NodeNodesList, processor: Processor, ctx: ProcessingContext, mode: ValType): Node? =
        if (mode == ValType.VALUE)
            processor.process(create(node), ctx, ValType.VALUE)
        else null

    private fun create(node: NodeNodesList): Node {
        val info = node.info
        return nodeWithGens(info, nodeAs(info, node.nodes.dropLast(node.nodes.size - 2)), node.nodes.asSequence().drop(2))
    }
}