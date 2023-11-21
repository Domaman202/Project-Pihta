package ru.DmN.pht.std.processors

import ru.DmN.pht.base.Processor
import ru.DmN.pht.base.ast.Node
import ru.DmN.pht.base.ast.NodeNodesList
import ru.DmN.pht.base.processor.utils.ProcessingContext
import ru.DmN.pht.base.processor.utils.ValType
import ru.DmN.pht.base.processors.INodeProcessor

object NRUnrollA : INodeProcessor<NodeNodesList> {
    override fun process(node: NodeNodesList, processor: Processor, ctx: ProcessingContext, mode: ValType): Node {
        var expr = processor.process(node.nodes[0], ctx, ValType.VALUE)!!
        for (i in 1 until node.nodes.size)
            expr = processor.process(node.nodes[i].apply { (nodes as MutableList<Node>).add(0, expr) }, ctx, ValType.VALUE)!!
        return expr
    }
}