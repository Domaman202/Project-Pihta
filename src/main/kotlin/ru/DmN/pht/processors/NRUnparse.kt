package ru.DmN.pht.processors

import ru.DmN.pht.ast.NodeValue
import ru.DmN.pht.processor.ctx.global
import ru.DmN.pht.utils.node.nodeValue
import ru.DmN.siberia.ast.NodeNodesList
import ru.DmN.siberia.processor.Processor
import ru.DmN.siberia.processor.ctx.ProcessingContext
import ru.DmN.siberia.processors.NRProgn
import ru.DmN.siberia.unparser.UnparserImpl
import ru.DmN.siberia.unparser.UnparsingContext
import ru.DmN.siberia.utils.vtype.VirtualType

object NRUnparse : IStdNodeProcessor<NodeNodesList> {
    override fun calc(node: NodeNodesList, processor: Processor, ctx: ProcessingContext): VirtualType =
        ctx.global.getType("String")

    override fun process(node: NodeNodesList, processor: Processor, ctx: ProcessingContext, valMode: Boolean): NodeValue =
        nodeValue(node.info, computeString(NRProgn.process(node, processor, ctx, true), processor, ctx))

    override fun computeString(node: NodeNodesList, processor: Processor, ctx: ProcessingContext): String =
        computeUnparse(NRProgn.process(node.copy(), processor, ctx, true), processor, ctx)

    private fun computeUnparse(node: NodeNodesList, processor: Processor, ctx: ProcessingContext): String {
        val unparser = UnparserImpl(processor.mp, 1024)
        val context = UnparsingContext.base()
        ctx.loadedModules.forEach { it.load(unparser, context) }
        node.nodes.forEach { unparser.unparse(it, context, 0) }
        return unparser.out.toString()
    }
}