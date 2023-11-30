package ru.DmN.pht.std.processors

import ru.DmN.pht.std.ast.NodeAGet
import ru.DmN.pht.std.utils.processNodes
import ru.DmN.siberia.Processor
import ru.DmN.siberia.ast.NodeNodesList
import ru.DmN.siberia.processor.ctx.ProcessingContext
import ru.DmN.siberia.processor.utils.ValType
import ru.DmN.siberia.processors.INodeProcessor
import ru.DmN.siberia.utils.VirtualType

object NRAGet : INodeProcessor<NodeNodesList> {
    override fun calc(node: NodeNodesList, processor: Processor, ctx: ProcessingContext): VirtualType? =
        processor.calc(node.nodes[0], ctx)!!.componentType

    override fun process(node: NodeNodesList, processor: Processor, ctx: ProcessingContext, mode: ValType): NodeAGet? =
        if (mode == ValType.VALUE) {
            val nodes = processor.processNodes(node, ctx, ValType.VALUE)
            NodeAGet(node.token.processed(), nodes[0], nodes[1])
        } else null
}