package ru.DmN.pht.std.processors

import ru.DmN.pht.std.ast.NodeIsAs
import ru.DmN.siberia.Processor
import ru.DmN.siberia.processor.ctx.ProcessingContext
import ru.DmN.siberia.processors.INodeProcessor
import ru.DmN.siberia.utils.VirtualType

object NRAsB : INodeProcessor<NodeIsAs> {
    override fun calc(node: NodeIsAs, processor: Processor, ctx: ProcessingContext): VirtualType? =
        node.type
}