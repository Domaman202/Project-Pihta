package ru.DmN.pht.std.processors

import ru.DmN.pht.base.Processor
import ru.DmN.pht.base.ast.NodeNodesList
import ru.DmN.pht.base.processor.utils.ProcessingContext
import ru.DmN.pht.base.processor.utils.ValType
import ru.DmN.pht.base.processors.INodeProcessor
import ru.DmN.pht.base.utils.VirtualType
import ru.DmN.pht.std.ast.NodeAGet
import ru.DmN.pht.std.utils.processNodes

object NRAGet : INodeProcessor<NodeNodesList> {
    override fun calc(node: NodeNodesList, processor: Processor, ctx: ProcessingContext): VirtualType? =
        processor.calc(node.nodes[0], ctx)!!.componentType

    override fun process(node: NodeNodesList, processor: Processor, ctx: ProcessingContext, mode: ValType): NodeAGet? =
        if (mode == ValType.VALUE) {
            val nodes = processor.processNodes(node, ctx, ValType.VALUE)
            NodeAGet(node.token.processed(), nodes[0], nodes[1])
        } else null
}