package ru.DmN.pht.processors

import ru.DmN.pht.jvm.utils.vtype.genericsAccept
import ru.DmN.pht.processor.ctx.castFrom
import ru.DmN.pht.processor.utils.computeList
import ru.DmN.pht.processor.utils.computeType
import ru.DmN.pht.utils.OrPair
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
        processor.calc(node.nodes[0], ctx)?.let { node.generics(it, processor, ctx) }

    override fun process(node: NodeNodesList, processor: Processor, ctx: ProcessingContext, valMode: Boolean): Node? =
        if (valMode) {
            val value = processor.process(node.nodes[0], ctx, true)!!
            val np = processor.get(value, ctx)
            val type = np.calc(value, processor, ctx)!!
            ctx.castFrom(type, node.generics(type, processor, ctx), value, processor, ctx)
        } else null

    private fun NodeNodesList.generics(type: VirtualType, processor: Processor, ctx: ProcessingContext): VVTWithGenerics {
        val iter = type.genericsAccept.iterator()
        return VVTWithGenerics(
            PhtVirtualType.of(type),
            processor.computeList(nodes[1], ctx)
                .asSequence()
                .map { Pair(iter.next(), OrPair.first<VirtualType, String>(processor.computeType(it, ctx))) }
                .toMap()
        )
    }
}