package ru.DmN.pht.std.processors

import ru.DmN.pht.base.Processor
import ru.DmN.pht.base.ast.Node
import ru.DmN.pht.base.ast.NodeNodesList
import ru.DmN.pht.base.processor.utils.ProcessingContext
import ru.DmN.pht.base.processor.utils.ValType
import ru.DmN.pht.base.processors.INodeProcessor
import ru.DmN.pht.base.utils.VirtualType
import ru.DmN.pht.std.ast.NodeAs
import ru.DmN.pht.std.processor.utils.global
import ru.DmN.pht.std.utils.computeString

object NRAs : INodeProcessor<NodeNodesList> {
    override fun calc(node: NodeNodesList, processor: Processor, ctx: ProcessingContext): VirtualType =
        ctx.global.getType(processor.computeString(node.nodes[0], ctx), processor.tp)

    override fun process(node: NodeNodesList, processor: Processor, ctx: ProcessingContext, mode: ValType): Node? =
        if (mode == ValType.VALUE) {
            val from = processor.calc(node.nodes[1], ctx)!!
            val to = calc(node, processor, ctx)
            if (from.isAssignableFrom(to))
                processor.process(node.nodes[1], ctx, ValType.VALUE)
            else NodeAs(node.token.processed(), mutableListOf(processor.process(node.nodes[1], ctx, ValType.VALUE)!!), to)
        } else null
}