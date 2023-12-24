package ru.DmN.pht.std.processors

import ru.DmN.pht.std.node.NodeTypes
import ru.DmN.pht.std.utils.mapMutable
import ru.DmN.siberia.Processor
import ru.DmN.siberia.ast.Node
import ru.DmN.siberia.ast.NodeNodesList
import ru.DmN.siberia.processor.ctx.ProcessingContext
import ru.DmN.siberia.processor.utils.ValType

object NRValn : IStdNodeProcessor<NodeNodesList> {
    override fun process(node: NodeNodesList, processor: Processor, ctx: ProcessingContext, mode: ValType): NodeNodesList =
        NodeNodesList(node.info.withType(NodeTypes.VALN_), node.nodes.mapMutable { processor.process(it, ctx, ValType.VALUE)!! })

    override fun computeList(node: NodeNodesList, processor: Processor, ctx: ProcessingContext): List<Node> =
        node.nodes
}