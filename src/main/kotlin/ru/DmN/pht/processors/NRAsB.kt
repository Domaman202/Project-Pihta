package ru.DmN.pht.processors

import ru.DmN.pht.ast.NodeIsAs
import ru.DmN.siberia.processor.Processor
import ru.DmN.siberia.processor.ctx.ProcessingContext
import ru.DmN.siberia.processors.INodeProcessor
import ru.DmN.siberia.utils.vtype.VirtualType

object NRAsB : INodeProcessor<NodeIsAs> {
    override fun calc(node: NodeIsAs, processor: Processor, ctx: ProcessingContext): VirtualType =
        node.type
}