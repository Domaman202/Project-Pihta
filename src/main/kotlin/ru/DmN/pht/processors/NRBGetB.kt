package ru.DmN.pht.processors

import ru.DmN.pht.ast.NodeBGet
import ru.DmN.siberia.processor.Processor
import ru.DmN.siberia.processor.ctx.ProcessingContext
import ru.DmN.siberia.processors.INodeProcessor
import ru.DmN.siberia.utils.vtype.VirtualType

object NRBGetB : INodeProcessor<NodeBGet> {
    override fun calc(node: NodeBGet, processor: Processor, ctx: ProcessingContext): VirtualType =
        node.variable.type
}