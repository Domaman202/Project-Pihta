package ru.DmN.pht.processors

import ru.DmN.pht.ast.NodeIsAs
import ru.DmN.pht.utils.computeType
import ru.DmN.pht.utils.node.NodeTypes.AS_
import ru.DmN.pht.utils.vtype.VTWithGenerics
import ru.DmN.siberia.ast.Node
import ru.DmN.siberia.ast.NodeNodesList
import ru.DmN.siberia.processor.Processor
import ru.DmN.siberia.processor.ctx.ProcessingContext
import ru.DmN.siberia.utils.vtype.VirtualType
import ru.DmN.siberia.utils.vtype.VirtualType.Companion.VOID

object NRAs : IStdNodeProcessor<NodeNodesList> {
    override fun calc(node: NodeNodesList, processor: Processor, ctx: ProcessingContext): VirtualType =
        processor.computeType(node.nodes[0], ctx)

    override fun process(node: NodeNodesList, processor: Processor, ctx: ProcessingContext, valMode: Boolean): Node? =
        if (valMode) {
            val from = processor.calc(node.nodes[1], ctx)
            val to = calc(node, processor, ctx)
            if (to !is VTWithGenerics && from?.isAssignableFrom(to) == true || from == VOID || from == null)
                processor.process(node.nodes[1], ctx, true)
            else NodeIsAs(node.info.withType(AS_), mutableListOf(processor.process(node.nodes[1], ctx, true)!!), from, to)
        } else null
}