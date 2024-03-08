package ru.DmN.pht.processors

import ru.DmN.pht.ast.NodeBody
import ru.DmN.pht.processor.ctx.BodyContext
import ru.DmN.pht.processor.ctx.bodyOrNull
import ru.DmN.pht.processor.ctx.with
import ru.DmN.pht.utils.node.NodeTypes.BODY_
import ru.DmN.siberia.ast.NodeNodesList
import ru.DmN.siberia.processor.Processor
import ru.DmN.siberia.processor.ctx.ProcessingContext
import ru.DmN.siberia.processor.utils.processNodesList
import ru.DmN.siberia.processors.INodeProcessor
import ru.DmN.siberia.utils.vtype.VirtualType

object NRBody : INodeProcessor<NodeNodesList> {
    override fun calc(node: NodeNodesList, processor: Processor, ctx: ProcessingContext): VirtualType? =
        if (node is NodeBody)
            processor.calc(node.nodes.last(), ctx(node, ctx))
        else {
            val context = ctx.with(BodyContext.of(ctx.bodyOrNull?.copy()))
            processor.calc(process(node, processor, context, true), context)
        }

    override fun process(node: NodeNodesList, processor: Processor, ctx: ProcessingContext, valMode: Boolean): NodeNodesList =
        NodeBody(node.info.withType(BODY_), node.copyNodes()).apply { processNodesList(this, processor, ctx(this, ctx), valMode) }

    private fun ctx(node: NodeBody, ctx: ProcessingContext): ProcessingContext =
        ctx.with(node.ctx ?: BodyContext.of(ctx.bodyOrNull).apply { node.ctx = this })
}