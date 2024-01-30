package ru.DmN.pht.processors

import ru.DmN.pht.processor.utils.global
import ru.DmN.pht.utils.VTWG
import ru.DmN.pht.utils.computeString
import ru.DmN.pht.utils.OrPair
import ru.DmN.siberia.Processor
import ru.DmN.siberia.ast.Node
import ru.DmN.siberia.ast.NodeNodesList
import ru.DmN.siberia.processor.ctx.ProcessingContext
import ru.DmN.siberia.processor.utils.ValType
import ru.DmN.siberia.processors.INodeProcessor
import ru.DmN.siberia.utils.VirtualType

object NRWithGens : INodeProcessor<NodeNodesList> {
    override fun calc(node: NodeNodesList, processor: Processor, ctx: ProcessingContext): VirtualType? =
        processor.calc(node.nodes[0], ctx)?.let { VTWG(it, node.generics(it, processor, ctx)) }

    override fun process(node: NodeNodesList, processor: Processor, ctx: ProcessingContext, mode: ValType): Node? =
        processor.process(node.nodes[0], ctx, mode)

    private fun NodeNodesList.generics(type: VirtualType, processor: Processor, ctx: ProcessingContext): Map<String, OrPair<VirtualType, String>> {
        val gctx = ctx.global
        val iter = type.generics.keys.iterator()
        return this.nodes
            .asSequence()
            .drop(1)
            .map { processor.computeString(it, ctx) }
            .map { gctx.getType(it, processor.tp) }
            .map { Pair(iter.next(), OrPair.first<VirtualType, String>(it)) }
            .toMap()
    }
}