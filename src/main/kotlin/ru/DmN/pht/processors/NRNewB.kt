package ru.DmN.pht.processors

import ru.DmN.pht.ast.NodeNew
import ru.DmN.siberia.processor.Processor
import ru.DmN.siberia.processor.ctx.ProcessingContext
import ru.DmN.siberia.processors.INodeProcessor
import ru.DmN.siberia.utils.vtype.VirtualType

object NRNewB : INodeProcessor<NodeNew> {
    override fun calc(node: NodeNew, processor: Processor, ctx: ProcessingContext): VirtualType =
        node.type
}