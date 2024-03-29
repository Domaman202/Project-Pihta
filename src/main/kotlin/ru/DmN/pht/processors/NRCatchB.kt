package ru.DmN.pht.processors

import ru.DmN.pht.ast.NodeCatch
import ru.DmN.siberia.processor.Processor
import ru.DmN.siberia.processor.ctx.ProcessingContext
import ru.DmN.siberia.processors.INodeProcessor
import ru.DmN.siberia.utils.vtype.VirtualType

object NRCatchB : INodeProcessor<NodeCatch> {
    override fun calc(node: NodeCatch, processor: Processor, ctx: ProcessingContext): VirtualType? =
        node.type
}