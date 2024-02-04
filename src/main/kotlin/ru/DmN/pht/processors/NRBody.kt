package ru.DmN.pht.processors

import ru.DmN.pht.ast.NodeBody
import ru.DmN.pht.node.NodeTypes.BODY_
import ru.DmN.pht.processor.ctx.BodyContext
import ru.DmN.pht.processor.utils.bodyOrNull
import ru.DmN.pht.processor.utils.with
import ru.DmN.siberia.Processor
import ru.DmN.siberia.ast.NodeNodesList
import ru.DmN.siberia.processor.ctx.ProcessingContext
import ru.DmN.siberia.processor.utils.ValType
import ru.DmN.siberia.processor.utils.ValType.VALUE
import ru.DmN.siberia.processor.utils.processNodesList
import ru.DmN.siberia.processors.INodeProcessor
import ru.DmN.siberia.utils.VirtualType

object NRBody : INodeProcessor<NodeNodesList> {
    override fun calc(node: NodeNodesList, processor: Processor, ctx: ProcessingContext): VirtualType? =
        if (node is NodeBody)
            processor.calc(node.nodes.last(), ctx(node, ctx))
        else {
            val context = ctx.with(BodyContext.of(ctx.bodyOrNull?.copy()))
            processor.calc(process(node, processor, context, VALUE), context)
        }

    override fun process(node: NodeNodesList, processor: Processor, ctx: ProcessingContext, mode: ValType): NodeNodesList =
        NodeBody(node.info.withType(BODY_), node.copyNodes()).apply { processNodesList(this, processor, ctx(this, ctx), mode) }

    private fun ctx(node: NodeBody, ctx: ProcessingContext): ProcessingContext =
        ctx.with(node.ctx ?: BodyContext.of(ctx.bodyOrNull).apply { node.ctx = this })
}