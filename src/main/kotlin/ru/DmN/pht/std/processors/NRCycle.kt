package ru.DmN.pht.std.processors

import ru.DmN.pht.base.Processor
import ru.DmN.pht.base.ast.NodeNodesList
import ru.DmN.pht.base.processor.utils.ProcessingContext
import ru.DmN.pht.base.processor.utils.ValType
import ru.DmN.pht.base.processors.INodeProcessor

object NRCycle : INodeProcessor<NodeNodesList> {
    override fun process(node: NodeNodesList, processor: Processor, ctx: ProcessingContext, mode: ValType): NodeNodesList {
        node.nodes[0] = processor.process(node.nodes[0], ctx, ValType.VALUE)!!
        for (i in 1 until node.nodes.size)
            node.nodes[i] = processor.process(node.nodes[i], ctx, ValType.NO_VALUE)!!
        return NodeNodesList(node.token.processed(), node.nodes)
    }
}