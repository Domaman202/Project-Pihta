package ru.DmN.pht.std.processors

import ru.DmN.pht.std.ast.NodeSet
import ru.DmN.pht.std.node.NodeTypes
import ru.DmN.siberia.Processor
import ru.DmN.siberia.processor.ctx.ProcessingContext
import ru.DmN.siberia.processor.utils.ValType
import ru.DmN.siberia.processors.INodeProcessor

object NRSet : INodeProcessor<NodeSet> {
    override fun process(node: NodeSet, processor: Processor, ctx: ProcessingContext, mode: ValType): NodeSet =
        NodeSet(node.info.withType(NodeTypes.SET_), mutableListOf(processor.process(node.nodes[0], ctx, ValType.VALUE)!!), node.name)
}