package ru.DmN.pht.std.processors

import ru.DmN.pht.std.node.NodeTypes
import ru.DmN.siberia.Processor
import ru.DmN.siberia.ast.NodeNodesList
import ru.DmN.siberia.processor.ctx.ProcessingContext
import ru.DmN.siberia.processor.utils.ValType
import ru.DmN.siberia.processors.INodeProcessor

object NRThrow : INodeProcessor<NodeNodesList> {
    override fun process(node: NodeNodesList, processor: Processor, ctx: ProcessingContext, mode: ValType): NodeNodesList =
        NodeNodesList(node.info.withType(NodeTypes.THROW_), mutableListOf(processor.process(node.nodes[0], ctx, mode)!!))
}