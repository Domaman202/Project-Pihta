package ru.DmN.pht.processors

import ru.DmN.pht.utils.node.NodeTypes.ARRAY_SIZE_
import ru.DmN.siberia.processor.Processor
import ru.DmN.siberia.ast.NodeNodesList
import ru.DmN.siberia.processor.ctx.ProcessingContext
import ru.DmN.siberia.processors.INodeProcessor
import ru.DmN.siberia.utils.vtype.VirtualType

object NRArraySize : INodeProcessor<NodeNodesList> {
    override fun calc(node: NodeNodesList, processor: Processor, ctx: ProcessingContext): VirtualType =
        VirtualType.INT

    override fun process(node: NodeNodesList, processor: Processor, ctx: ProcessingContext, valMode: Boolean): NodeNodesList? =
        if (valMode)
            NodeNodesList(node.info.withType(ARRAY_SIZE_), mutableListOf(processor.process(node.nodes[0], ctx, true)!!))
        else null
}