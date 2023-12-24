package ru.DmN.pht.std.processors

import ru.DmN.pht.std.node.NodeTypes
import ru.DmN.siberia.Processor
import ru.DmN.siberia.ast.NodeNodesList
import ru.DmN.siberia.processor.ctx.ProcessingContext
import ru.DmN.siberia.processor.utils.ValType
import ru.DmN.siberia.processors.INodeProcessor

object NRCycle : INodeProcessor<NodeNodesList> {
    override fun process(node: NodeNodesList, processor: Processor, ctx: ProcessingContext, mode: ValType): NodeNodesList {
        node.nodes[0] = processor.process(node.nodes[0], ctx, ValType.VALUE)!!
        for (i in 1 until node.nodes.size)
            node.nodes[i] = processor.process(node.nodes[i], ctx, ValType.NO_VALUE)!!
        return NodeNodesList(node.info.withType(NodeTypes.CYCLE_), node.nodes)
    }
}