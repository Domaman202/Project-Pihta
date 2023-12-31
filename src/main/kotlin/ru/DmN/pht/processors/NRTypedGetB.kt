package ru.DmN.pht.processors

import ru.DmN.pht.ast.NodeTypedGet
import ru.DmN.siberia.Processor
import ru.DmN.siberia.processor.ctx.ProcessingContext
import ru.DmN.siberia.processors.INodeProcessor
import ru.DmN.siberia.utils.VirtualType

object NRTypedGetB : INodeProcessor<NodeTypedGet> {
    override fun calc(node: NodeTypedGet, processor: Processor, ctx: ProcessingContext): VirtualType =
        node.type
}