package ru.DmN.pht.processors

import ru.DmN.pht.utils.mapMutable
import ru.DmN.pht.utils.node.NodeTypes.VALN_
import ru.DmN.siberia.Processor
import ru.DmN.siberia.ast.Node
import ru.DmN.siberia.ast.NodeNodesList
import ru.DmN.siberia.processor.ctx.ProcessingContext

object NRValn : IStdNodeProcessor<NodeNodesList> {
    override fun process(node: NodeNodesList, processor: Processor, ctx: ProcessingContext, valMode: Boolean): NodeNodesList =
        NodeNodesList(node.info.withType(VALN_), node.nodes.mapMutable { processor.process(it, ctx, true)!! })

    override fun computeList(node: NodeNodesList, processor: Processor, ctx: ProcessingContext): List<Node> =
        node.nodes
}