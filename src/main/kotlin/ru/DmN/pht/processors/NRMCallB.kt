package ru.DmN.pht.std.processors

import ru.DmN.pht.std.ast.NodeMCall
import ru.DmN.siberia.Processor
import ru.DmN.siberia.processor.ctx.ProcessingContext
import ru.DmN.siberia.processors.INodeProcessor
import ru.DmN.siberia.utils.VirtualType

object NRMCallB : INodeProcessor<NodeMCall> {
    override fun calc(node: NodeMCall, processor: Processor, ctx: ProcessingContext): VirtualType =
        node.generic ?: node.method.rettype
}