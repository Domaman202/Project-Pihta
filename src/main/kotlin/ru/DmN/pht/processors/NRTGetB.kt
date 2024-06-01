package ru.DmN.pht.processors

import ru.DmN.pht.ast.NodeTGet
import ru.DmN.siberia.processor.Processor
import ru.DmN.siberia.processor.ctx.ProcessingContext
import ru.DmN.siberia.processors.INodeProcessor
import ru.DmN.siberia.utils.vtype.VirtualType

object NRTGetB : INodeProcessor<NodeTGet> {
    override fun calc(node: NodeTGet, processor: Processor, ctx: ProcessingContext): VirtualType =
        node.type
}