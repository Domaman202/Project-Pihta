package ru.DmN.pht.processors

import ru.DmN.pht.ast.NodeMetaNodesList
import ru.DmN.pht.utils.node.NodeTypes.PROGN_B_
import ru.DmN.siberia.processor.Processor
import ru.DmN.siberia.ast.Node
import ru.DmN.siberia.processor.ctx.ProcessingContext
import ru.DmN.siberia.processors.NRProgn
import ru.DmN.siberia.utils.vtype.VirtualType

object NRPrognB : IStdNodeProcessor<NodeMetaNodesList> {
    override fun calc(node: NodeMetaNodesList, processor: Processor, ctx: ProcessingContext): VirtualType? =
        NRProgn.calc(node, processor, ctx)

    override fun process(node: NodeMetaNodesList, processor: Processor, ctx: ProcessingContext, valMode: Boolean): NodeMetaNodesList =
        NodeMetaNodesList(
            node.info.withType(PROGN_B_),
            if (node.nodes.isEmpty())
                ArrayList()
            else {
                val list =
                    node.nodes
                        .dropLast(1) // todo: drop last for sequence
                        .asSequence()
                        .map { processor.process(it, ctx, false) }
                        .filterNotNull()
                        .toMutableList()
                processor.process(node.nodes.last(), ctx, true)?.let { list += it }
                list
            }
        )

    override fun computeList(node: NodeMetaNodesList, processor: Processor, ctx: ProcessingContext): List<Node> =
        node.nodes
}