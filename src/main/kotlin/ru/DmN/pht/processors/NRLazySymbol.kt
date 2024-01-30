package ru.DmN.pht.processors

import ru.DmN.pht.ast.NodeLazySymbol
import ru.DmN.pht.ast.NodeValue
import ru.DmN.siberia.Processor
import ru.DmN.siberia.ast.Node
import ru.DmN.siberia.processor.ctx.ProcessingContext
import ru.DmN.siberia.processor.utils.ValType

object NRLazySymbol : IStdNodeProcessor<NodeLazySymbol> {
    override fun process(node: NodeLazySymbol, processor: Processor, ctx: ProcessingContext, mode: ValType): Node? =
        if (mode == ValType.VALUE)
            NodeValue.of(node.info, NodeValue.Type.STRING, computeString(node, processor, ctx))
        else null

    override fun computeString(node: NodeLazySymbol, processor: Processor, ctx: ProcessingContext): String =
        if (node.symbol.get() == null)
            NRSymbol.computeString(node, processor, ctx).apply { node.symbol.set(this) }
        else node.symbol.get()!!
}