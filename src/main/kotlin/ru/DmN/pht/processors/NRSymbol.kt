package ru.DmN.pht.processors

import ru.DmN.pht.processor.ctx.global
import ru.DmN.pht.utils.computeString
import ru.DmN.pht.utils.node.nodeValue
import ru.DmN.siberia.ast.Node
import ru.DmN.siberia.ast.NodeNodesList
import ru.DmN.siberia.processor.Processor
import ru.DmN.siberia.processor.ctx.ProcessingContext
import ru.DmN.siberia.utils.vtype.VirtualType

object NRSymbol : IStdNodeProcessor<NodeNodesList> {
    override fun calc(node: NodeNodesList, processor: Processor, ctx: ProcessingContext): VirtualType =
        ctx.global.getType("String", processor.tp)

    override fun process(node: NodeNodesList, processor: Processor, ctx: ProcessingContext, valMode: Boolean): Node? =
        if (valMode)
            nodeValue(node.info, computeString(node, processor, ctx))
        else null

    override fun computeString(node: NodeNodesList, processor: Processor, ctx: ProcessingContext): String =
        node.nodes.asSequence().map { processor.computeString(it, ctx) }.reduce { acc, s -> acc + s }
}