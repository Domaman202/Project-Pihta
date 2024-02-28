package ru.DmN.pht.processors

import ru.DmN.pht.utils.node.NodeTypes.THROW_
import ru.DmN.siberia.Processor
import ru.DmN.siberia.ast.NodeNodesList
import ru.DmN.siberia.processor.ctx.ProcessingContext
import ru.DmN.siberia.processors.INodeProcessor

object NRThrow : INodeProcessor<NodeNodesList> {
    override fun process(node: NodeNodesList, processor: Processor, ctx: ProcessingContext, valMode: Boolean): NodeNodesList =
        NodeNodesList(node.info.withType(THROW_), mutableListOf(processor.process(node.nodes[0], ctx, valMode)!!))
}