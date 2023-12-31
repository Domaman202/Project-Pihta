package ru.DmN.pht.processors

import ru.DmN.pht.std.ast.NodeModifierNodesList
import ru.DmN.pht.std.node.NodeTypes.PROGN_B_
import ru.DmN.pht.std.processors.IStdNodeProcessor
import ru.DmN.siberia.Processor
import ru.DmN.siberia.ast.Node
import ru.DmN.siberia.ast.NodeNodesList
import ru.DmN.siberia.processor.ctx.ProcessingContext
import ru.DmN.siberia.processor.utils.ValType
import ru.DmN.siberia.processors.NRProgn
import ru.DmN.siberia.utils.VirtualType

object NRPrognB : IStdNodeProcessor<NodeModifierNodesList> {
    override fun calc(node: NodeModifierNodesList, processor: Processor, ctx: ProcessingContext): VirtualType? =
        NRProgn.calc(node, processor, ctx)

    override fun process(node: NodeModifierNodesList, processor: Processor, ctx: ProcessingContext, mode: ValType): NodeNodesList =
        NodeModifierNodesList(
            node.info.withType(PROGN_B_),
            if (node.nodes.isEmpty())
                ArrayList()
            else {
                val list =
                    node.nodes
                        .dropLast(1) // todo: drop last for sequence
                        .asSequence()
                        .map { processor.process(it, ctx, ValType.NO_VALUE) }
                        .filterNotNull()
                        .toMutableList()
                processor.process(node.nodes.last(), ctx, ValType.VALUE)?.let { list += it }
                list
            }
        )

    override fun computeList(node: NodeModifierNodesList, processor: Processor, ctx: ProcessingContext): List<Node> =
        node.nodes
}