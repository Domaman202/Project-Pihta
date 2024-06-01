package ru.DmN.pht.processors

import ru.DmN.pht.ast.NodeBSet
import ru.DmN.pht.utils.node.NodeTypes.BSET_
import ru.DmN.siberia.ast.NodeNodesList
import ru.DmN.siberia.processor.Processor
import ru.DmN.siberia.processor.ctx.ProcessingContext
import ru.DmN.siberia.processors.INodeProcessor

object NRBSet : INodeProcessor<NodeNodesList> {
    override fun process(node: NodeNodesList, processor: Processor, ctx: ProcessingContext, valMode: Boolean): NodeBSet {
        val pair = NRBGet.find(node, processor, ctx)
        return NodeBSet(node.info.withType(BSET_), pair.first, pair.second, processor.process(node.nodes[2], ctx, true)!!)
    }
}