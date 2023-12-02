package ru.DmN.pht.std.processors

import ru.DmN.pht.std.utils.computeGenericsOr
import ru.DmN.siberia.Processor
import ru.DmN.siberia.ast.Node
import ru.DmN.siberia.ast.NodeNodesList
import ru.DmN.siberia.processor.ctx.ProcessingContext
import ru.DmN.siberia.processors.SimpleNR
import ru.DmN.siberia.utils.VirtualType

open class SimpleStdNR : SimpleNR<NodeNodesList>(), IStdNodeProcessor<NodeNodesList> {
    override fun computeList(node: NodeNodesList, processor: Processor, ctx: ProcessingContext): List<Node> =
        node.nodes

    override fun computeGenerics(node: NodeNodesList, processor: Processor, ctx: ProcessingContext): List<VirtualType>? =
        node.nodes.lastOrNull()?.let { processor.computeGenericsOr(it, ctx) }
}