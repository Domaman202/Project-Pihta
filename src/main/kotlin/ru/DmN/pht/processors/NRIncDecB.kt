package ru.DmN.pht.processors

import ru.DmN.pht.ast.NodeIncDec
import ru.DmN.siberia.Processor
import ru.DmN.siberia.processor.ctx.ProcessingContext
import ru.DmN.siberia.processors.INodeProcessor
import ru.DmN.siberia.utils.VirtualType

object NRIncDecB : INodeProcessor<NodeIncDec> {
    override fun calc(node: NodeIncDec, processor: Processor, ctx: ProcessingContext): VirtualType? =
        NRGetOrName.calc(node.info, node.name, processor, ctx)
}