package ru.DmN.pht.processors

import ru.DmN.pht.ast.NodeMCall
import ru.DmN.siberia.processor.Processor
import ru.DmN.siberia.processor.ctx.ProcessingContext
import ru.DmN.siberia.processors.INodeProcessor
import ru.DmN.siberia.utils.vtype.VirtualType

object NRMCallB : INodeProcessor<NodeMCall> {
    override fun calc(node: NodeMCall, processor: Processor, ctx: ProcessingContext): VirtualType =
        node.generic ?: node.method.rettype
}