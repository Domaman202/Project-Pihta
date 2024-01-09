package ru.DmN.pht.processors

import ru.DmN.pht.ast.NodeRFn
import ru.DmN.siberia.Processor
import ru.DmN.siberia.ast.NodeNodesList
import ru.DmN.siberia.processor.ctx.ProcessingContext
import ru.DmN.siberia.processors.INodeProcessor
import ru.DmN.siberia.utils.VirtualType

object NRRFnB : INodeProcessor<NodeRFn> {
    override fun calc(node: NodeRFn, processor: Processor, ctx: ProcessingContext): VirtualType =
        node.type
}