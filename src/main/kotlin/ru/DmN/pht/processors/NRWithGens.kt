package ru.DmN.pht.processors

import ru.DmN.pht.jvm.utils.vtype.generics
import ru.DmN.pht.processor.ctx.global
import ru.DmN.pht.utils.OrPair
import ru.DmN.pht.utils.computeString
import ru.DmN.pht.utils.toMap
import ru.DmN.pht.utils.vtype.PhtVirtualType
import ru.DmN.pht.utils.vtype.VVTWithGenerics
import ru.DmN.siberia.ast.Node
import ru.DmN.siberia.ast.NodeNodesList
import ru.DmN.siberia.processor.Processor
import ru.DmN.siberia.processor.ctx.ProcessingContext
import ru.DmN.siberia.processors.INodeProcessor
import ru.DmN.siberia.utils.vtype.VirtualType

object NRWithGens : INodeProcessor<NodeNodesList> {
    override fun calc(node: NodeNodesList, processor: Processor, ctx: ProcessingContext): VirtualType? =
        processor.calc(node.nodes[0], ctx)?.let { VVTWithGenerics(it as PhtVirtualType, node.generics(it, processor, ctx)) }

    override fun process(node: NodeNodesList, processor: Processor, ctx: ProcessingContext, valMode: Boolean): Node? =
        processor.process(node.nodes[0], ctx, valMode)

    private fun NodeNodesList.generics(type: VirtualType, processor: Processor, ctx: ProcessingContext): Map<String, OrPair<VirtualType, String>> {
        val gctx = ctx.global
        val iter = type.generics.keys.iterator()
        return this.nodes
            .stream()
            .skip(1)
            .map { Pair(iter.next(), OrPair.first<VirtualType, String>(gctx.getType(processor.computeString(it, ctx)))) }
            .toMap()
    }
}