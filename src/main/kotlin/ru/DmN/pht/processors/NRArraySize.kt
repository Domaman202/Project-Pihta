package ru.DmN.pht.processors

import ru.DmN.pht.node.NodeTypes.ARRAY_SIZE_
import ru.DmN.siberia.Processor
import ru.DmN.siberia.ast.NodeNodesList
import ru.DmN.siberia.processor.ctx.ProcessingContext
import ru.DmN.siberia.processor.utils.ValType
import ru.DmN.siberia.processor.utils.ValType.VALUE
import ru.DmN.siberia.processors.INodeProcessor
import ru.DmN.siberia.utils.VirtualType

object NRArraySize : INodeProcessor<NodeNodesList> {
    override fun calc(node: NodeNodesList, processor: Processor, ctx: ProcessingContext): VirtualType =
        VirtualType.INT

    override fun process(node: NodeNodesList, processor: Processor, ctx: ProcessingContext, mode: ValType): NodeNodesList? =
        if (mode == VALUE)
            NodeNodesList(node.info.withType(ARRAY_SIZE_), mutableListOf(processor.process(node.nodes[0], ctx, VALUE)!!))
        else null
}