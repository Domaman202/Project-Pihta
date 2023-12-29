package ru.DmN.pht.std.processors

import ru.DmN.pht.std.ast.NodeAs
import ru.DmN.siberia.Processor
import ru.DmN.siberia.processor.ctx.ProcessingContext
import ru.DmN.siberia.processors.INodeProcessor
import ru.DmN.siberia.utils.VirtualType

object NRAsB : INodeProcessor<NodeAs> {
    override fun calc(node: NodeAs, processor: Processor, ctx: ProcessingContext): VirtualType? =
        node.type
}