package ru.DmN.pht.processors

import ru.DmN.pht.ast.NodeLazySymbol
import ru.DmN.pht.utils.node.nodeValue
import ru.DmN.siberia.processor.Processor
import ru.DmN.siberia.ast.Node
import ru.DmN.siberia.processor.ctx.ProcessingContext

object NRLazySymbol : IStdNodeProcessor<NodeLazySymbol> {
    override fun process(node: NodeLazySymbol, processor: Processor, ctx: ProcessingContext, valMode: Boolean): Node? =
        if (valMode)
            nodeValue(node.info, computeString(node, processor, ctx))
        else null

    override fun computeString(node: NodeLazySymbol, processor: Processor, ctx: ProcessingContext): String =
        if (node.symbol.get() == null)
            NRSymbol.computeString(node, processor, ctx).apply { node.symbol.set(this) }
        else node.symbol.get()!!
}