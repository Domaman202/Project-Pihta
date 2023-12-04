package ru.DmN.pht.std.processors

import ru.DmN.pht.std.ast.NodeAs
import ru.DmN.pht.std.ast.NodeGensNodesList
import ru.DmN.pht.std.processor.utils.global
import ru.DmN.pht.std.utils.computeString
import ru.DmN.siberia.Processor
import ru.DmN.siberia.ast.Node
import ru.DmN.siberia.processor.ctx.ProcessingContext
import ru.DmN.siberia.processor.utils.ValType
import ru.DmN.siberia.utils.VTDynamic
import ru.DmN.siberia.utils.VirtualType
import ru.DmN.siberia.utils.text

object NRAs : IStdNodeProcessor<NodeGensNodesList> {
    override fun calc(node: NodeGensNodesList, processor: Processor, ctx: ProcessingContext): VirtualType =
        ctx.global.getType(processor.computeString(node.nodes[0], ctx), processor.tp)

    override fun process(node: NodeGensNodesList, processor: Processor, ctx: ProcessingContext, mode: ValType): Node? =
        if (mode == ValType.VALUE) {
            val from = processor.calc(node.nodes[1], ctx)
            val to = calc(node, processor, ctx)
            if (node.generics.isEmpty() && from?.isAssignableFrom(to) == true || from == VirtualType.VOID || from == null)
                processor.process(node.nodes[1], ctx, ValType.VALUE)
            else NodeAs(node.token.processed(), mutableListOf(processor.process(node.nodes[1], ctx, ValType.VALUE)!!), node.generics, to)
        } else null

    override fun computeGenerics(node: NodeGensNodesList, processor: Processor, ctx: ProcessingContext): List<VirtualType> =
        node.generics
}