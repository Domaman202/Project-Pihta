package ru.DmN.pht.processors

import ru.DmN.pht.ast.NodeNamedList
import ru.DmN.pht.processor.ctx.NamedBodyContext
import ru.DmN.pht.processor.ctx.bodyOrNull
import ru.DmN.pht.processor.ctx.with
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
        NRProgn.calc(node, processor, ctxOf(processor.computeString(node.nodes[0], ctx), ctx))

    override fun process(node: NodeNodesList, processor: Processor, ctx: ProcessingContext, valMode: Boolean): NodeNamedList {
        val name = processor.computeString(node.nodes[0], ctx)
        return NodeNamedList(node.info.processed, node.nodes.drop(1).toMutableList(), name)
            .apply { processNodesList(this, processor, ctxOf(name, ctx), valMode) }
    }

    private fun ctxOf(name: String, ctx: ProcessingContext) =
        ctx.with(NamedBodyContext.of(ctx.bodyOrNull, name))
}