package ru.DmN.pht.processors

import ru.DmN.pht.ast.NodeIsAs
import ru.DmN.pht.node.NodeTypes
import ru.DmN.pht.utils.VTWG
import ru.DmN.pht.utils.computeType
import ru.DmN.siberia.Processor
import ru.DmN.siberia.ast.Node
import ru.DmN.siberia.ast.NodeNodesList
import ru.DmN.siberia.processor.ctx.ProcessingContext
import ru.DmN.siberia.processor.utils.ValType
import ru.DmN.siberia.processor.utils.ValType.VALUE
import ru.DmN.siberia.utils.VirtualType

object NRAs : IStdNodeProcessor<NodeNodesList> {
    override fun calc(node: NodeNodesList, processor: Processor, ctx: ProcessingContext): VirtualType =
        processor.computeType(node.nodes[0], ctx)

    override fun process(node: NodeNodesList, processor: Processor, ctx: ProcessingContext, mode: ValType): Node? =
        if (mode == VALUE) {
            val from = processor.calc(node.nodes[1], ctx)
            val to = calc(node, processor, ctx)
            if (to !is VTWG && from?.isAssignableFrom(to) == true || from == VirtualType.VOID || from == null)
                processor.process(node.nodes[1], ctx, VALUE)
            else NodeIsAs(node.info.withType(NodeTypes.AS_), mutableListOf(processor.process(node.nodes[1], ctx, VALUE)!!), to)
        } else null
}