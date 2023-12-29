package ru.DmN.pht.std.processors

import ru.DmN.pht.std.processor.utils.global
import ru.DmN.pht.std.utils.VTWG
import ru.DmN.pht.std.utils.computeString
import ru.DmN.siberia.Processor
import ru.DmN.siberia.ast.Node
import ru.DmN.siberia.ast.NodeNodesList
import ru.DmN.siberia.processor.ctx.ProcessingContext
import ru.DmN.siberia.processor.utils.ValType
import ru.DmN.siberia.processors.INodeProcessor
import ru.DmN.siberia.utils.VirtualType

object NRWithGens : INodeProcessor<NodeNodesList> {
    override fun calc(node: NodeNodesList, processor: Processor, ctx: ProcessingContext): VirtualType? =
        processor.calc(node.nodes[0], ctx)?.let { VTWG(it, node.generics(processor, ctx)) }

    override fun process(node: NodeNodesList, processor: Processor, ctx: ProcessingContext, mode: ValType): Node? =
        processor.process(node.nodes[0], ctx, mode)

    private fun NodeNodesList.generics(processor: Processor, ctx: ProcessingContext): List<VirtualType> {
        val gctx = ctx.global
        return this.nodes
            .asSequence()
            .drop(1)
            .map { processor.computeString(it, ctx) }
            .map { gctx.getType(it, processor.tp) }
            .toList()
    }
}