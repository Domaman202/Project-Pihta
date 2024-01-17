package ru.DmN.pht.processors

import ru.DmN.pht.std.ast.NodeCatch
import ru.DmN.siberia.Processor
import ru.DmN.siberia.processor.ctx.ProcessingContext
import ru.DmN.siberia.processors.INodeProcessor
import ru.DmN.siberia.utils.VirtualType

object NRCatchB : INodeProcessor<NodeCatch> {
    override fun calc(node: NodeCatch, processor: Processor, ctx: ProcessingContext): VirtualType? =
        node.type
}