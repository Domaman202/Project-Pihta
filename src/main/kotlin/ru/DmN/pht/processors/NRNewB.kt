package ru.DmN.pht.std.processors

import ru.DmN.pht.std.ast.NodeNew
import ru.DmN.siberia.Processor
import ru.DmN.siberia.processor.ctx.ProcessingContext
import ru.DmN.siberia.processors.INodeProcessor
import ru.DmN.siberia.utils.VirtualType

object NRNewB : INodeProcessor<NodeNew> {
    override fun calc(node: NodeNew, processor: Processor, ctx: ProcessingContext): VirtualType =
        node.type
}