package ru.DmN.pht.processors

import ru.DmN.pht.ast.NodeMath
import ru.DmN.siberia.processor.Processor
import ru.DmN.siberia.processor.ctx.ProcessingContext
import ru.DmN.siberia.processors.INodeProcessor
import ru.DmN.siberia.utils.vtype.VirtualType

object NRMathB : INodeProcessor<NodeMath> {
    override fun calc(node: NodeMath, processor: Processor, ctx: ProcessingContext): VirtualType =
        node.type
}