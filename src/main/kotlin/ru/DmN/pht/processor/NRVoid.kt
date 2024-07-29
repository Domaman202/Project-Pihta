package ru.DmN.pht.processor

import ru.DmN.siberia.ast.Node
import ru.DmN.siberia.processor.Processor
import ru.DmN.siberia.processor.ctx.ProcessingContext
import ru.DmN.siberia.processors.INodeProcessor
import ru.DmN.siberia.utils.vtype.VirtualType

object NRVoid : INodeProcessor<Node> {
    override fun calc(node: Node, processor: Processor, ctx: ProcessingContext): VirtualType =
        VirtualType.VOID
}