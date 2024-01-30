package ru.DmN.pht.processors

import ru.DmN.pht.node.NodeTypes
import ru.DmN.siberia.Processor
import ru.DmN.siberia.ast.NodeNodesList
import ru.DmN.siberia.processor.ctx.ProcessingContext
import ru.DmN.siberia.processor.utils.ValType
import ru.DmN.siberia.processors.INodeProcessor
import ru.DmN.siberia.utils.VirtualType

object NRArraySize : INodeProcessor<NodeNodesList> {
    override fun calc(node: NodeNodesList, processor: Processor, ctx: ProcessingContext): VirtualType =
        VirtualType.INT

    override fun process(node: NodeNodesList, processor: Processor, ctx: ProcessingContext, mode: ValType): NodeNodesList? =
        if (mode == ValType.VALUE)
            NodeNodesList(node.info.withType(NodeTypes.ARRAY_SIZE_), mutableListOf(processor.process(node.nodes[0], ctx, ValType.VALUE)!!))
        else null
}