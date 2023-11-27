package ru.DmN.pht.std.processors

import ru.DmN.siberia.Processor
import ru.DmN.siberia.ast.Node
import ru.DmN.siberia.ast.NodeNodesList
import ru.DmN.siberia.processor.utils.ProcessingContext
import ru.DmN.siberia.processors.SimpleNR

open class SimpleStdNR : SimpleNR<NodeNodesList>(), IStdNodeProcessor<NodeNodesList> {
    override val isComputeList: Boolean
        get() = true

    override fun computeList(node: NodeNodesList, processor: Processor, ctx: ProcessingContext): List<Node> =
        node.nodes
}