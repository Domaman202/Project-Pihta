package ru.DmN.pht.std.processors

import ru.DmN.siberia.Processor
import ru.DmN.siberia.ast.Node
import ru.DmN.siberia.ast.NodeNodesList
import ru.DmN.siberia.processor.ctx.ProcessingContext
import ru.DmN.siberia.processor.utils.ValType
import ru.DmN.siberia.processors.INodeProcessor
import ru.DmN.siberia.utils.VirtualType

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