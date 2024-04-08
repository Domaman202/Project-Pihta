package ru.DmN.pht.processors

import ru.DmN.pht.ast.NodeNamedList
import ru.DmN.pht.processor.utils.computeString
import ru.DmN.pht.utils.node.processed
import ru.DmN.siberia.ast.NodeNodesList
import ru.DmN.siberia.processor.Processor
import ru.DmN.siberia.processor.ctx.ProcessingContext
import ru.DmN.siberia.processor.utils.processNodesList
import ru.DmN.siberia.processors.INodeProcessor
import ru.DmN.siberia.processors.NRProgn
import ru.DmN.siberia.utils.vtype.VirtualType

object NRNamedList : INodeProcessor<NodeNodesList> {
    override fun calc(node: NodeNodesList, processor: Processor, ctx: ProcessingContext): VirtualType? =
        NRProgn.calc(node, processor, ctx)

    override fun process(node: NodeNodesList, processor: Processor, ctx: ProcessingContext, valMode: Boolean): NodeNamedList =
        NodeNamedList(node.info.processed, node.nodes.drop(1).toMutableList(), processor.computeString(node.nodes[0], ctx))
            .apply { processNodesList(this, processor, ctx, valMode) }
}