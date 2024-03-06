package ru.DmN.pht.processors

import ru.DmN.pht.utils.node.NodeTypes.CYCLE_
import ru.DmN.siberia.processor.Processor
import ru.DmN.siberia.ast.NodeNodesList
import ru.DmN.siberia.processor.ctx.ProcessingContext
import ru.DmN.siberia.processors.INodeProcessor

object NRCycle : INodeProcessor<NodeNodesList> {
    override fun process(node: NodeNodesList, processor: Processor, ctx: ProcessingContext, valMode: Boolean): NodeNodesList {
        node.nodes[0] = processor.process(node.nodes[0], ctx, true)!!
        for (i in 1 until node.nodes.size)
            node.nodes[i] = processor.process(node.nodes[i], ctx, false)!!
        return NodeNodesList(node.info.withType(CYCLE_), node.nodes)
    }
}