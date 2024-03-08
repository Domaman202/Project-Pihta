package ru.DmN.pht.processors

import ru.DmN.pht.ast.NodeNs
import ru.DmN.pht.processor.ctx.global
import ru.DmN.pht.processor.ctx.with
import ru.DmN.pht.utils.computeString
import ru.DmN.pht.utils.node.NodeTypes.NS_
import ru.DmN.siberia.ast.NodeNodesList
import ru.DmN.siberia.processor.Processor
import ru.DmN.siberia.processor.ctx.ProcessingContext
import ru.DmN.siberia.processor.utils.processNodesList
import ru.DmN.siberia.processors.INodeProcessor

object NRNs : INodeProcessor<NodeNodesList> { // todo: calc
    override fun process(node: NodeNodesList, processor: Processor, ctx: ProcessingContext, valMode: Boolean): NodeNs {
        val ns = processor.computeString(node.nodes.first(), ctx)
        val new = NodeNs(node.info.withType(NS_), node.nodes.drop(1).toMutableList(), ns)
        processNodesList(new, processor, ctx.with(ctx.global.with(ns)), valMode)
        return new
    }
}