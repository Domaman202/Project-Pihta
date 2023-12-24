package ru.DmN.pht.std.processors

import ru.DmN.pht.std.ast.NodeNewArray
import ru.DmN.siberia.Processor
import ru.DmN.siberia.processor.ctx.ProcessingContext
import ru.DmN.siberia.processors.INodeProcessor
import ru.DmN.siberia.utils.VirtualType

object NRNewArrayB : INodeProcessor<NodeNewArray> {
    override fun calc(node: NodeNewArray, processor: Processor, ctx: ProcessingContext): VirtualType =
        node.type.arrayType
}