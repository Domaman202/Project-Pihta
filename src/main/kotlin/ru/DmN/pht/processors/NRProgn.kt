package ru.DmN.pht.processors

import ru.DmN.siberia.processor.Processor
import ru.DmN.siberia.ast.Node
import ru.DmN.siberia.ast.NodeNodesList
import ru.DmN.siberia.processor.ctx.ProcessingContext
import ru.DmN.siberia.processors.NRProgn
import ru.DmN.siberia.utils.vtype.VirtualType

object NRProgn : IComputableProcessor<NodeNodesList> {
    override fun calc(node: NodeNodesList, processor: Processor, ctx: ProcessingContext): VirtualType? =
        NRProgn.calc(node, processor, ctx)

    override fun process(node: NodeNodesList, processor: Processor, ctx: ProcessingContext, valMode: Boolean): Node =
        NRProgn.process(node, processor, ctx, valMode)

    override fun computeList(node: NodeNodesList, processor: Processor, ctx: ProcessingContext): List<Node> =
        node.nodes
}