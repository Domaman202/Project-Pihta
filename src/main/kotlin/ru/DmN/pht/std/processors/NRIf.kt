package ru.DmN.pht.std.processors

import ru.DmN.pht.base.Processor
import ru.DmN.pht.base.ast.Node
import ru.DmN.pht.base.ast.NodeNodesList
import ru.DmN.pht.base.processor.ProcessingContext
import ru.DmN.pht.base.processor.ValType
import ru.DmN.pht.base.processors.INodeProcessor
import ru.DmN.pht.base.processors.NRDefault
import ru.DmN.pht.base.utils.VirtualType

object NRIf : INodeProcessor<NodeNodesList> {
    override fun calc(node: NodeNodesList, processor: Processor, ctx: ProcessingContext): VirtualType? =
        if (node.nodes.size == 3)
            processor.calc(node.nodes[1], ctx)
        else null

    override fun process(node: NodeNodesList, processor: Processor, ctx: ProcessingContext, mode: ValType): Node {
        node.nodes[0] = processor.process(node.nodes[0], ctx, ValType.VALUE)!!
        node.nodes[1] = processor.process(node.nodes[1], ctx, mode)!!
        if (node.nodes.size == 3)
            node.nodes[2] = processor.process(node.nodes[2], ctx, mode)!!
        return node
    }
}