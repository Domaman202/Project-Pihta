package ru.DmN.pht.processors

import ru.DmN.pht.ast.NodeType
import ru.DmN.siberia.Processor
import ru.DmN.siberia.processor.ctx.ProcessingContext
import ru.DmN.siberia.processors.INodeProcessor
import ru.DmN.siberia.utils.vtype.VirtualType

object NRObjB : INodeProcessor<NodeType> {
    override fun calc(node: NodeType, processor: Processor, ctx: ProcessingContext): VirtualType =
        node.type
}