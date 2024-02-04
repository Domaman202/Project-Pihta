package ru.DmN.pht.processors

import ru.DmN.pht.ast.NodeNs
import ru.DmN.pht.node.NodeTypes.NS_
import ru.DmN.pht.processor.utils.global
import ru.DmN.pht.processor.utils.with
import ru.DmN.pht.utils.computeString
import ru.DmN.siberia.Processor
import ru.DmN.siberia.ast.NodeNodesList
import ru.DmN.siberia.processor.ctx.ProcessingContext
import ru.DmN.siberia.processor.utils.ValType
import ru.DmN.siberia.processor.utils.processNodesList
import ru.DmN.siberia.processors.INodeProcessor

object NRNs : INodeProcessor<NodeNodesList> { // todo: calc
    override fun process(node: NodeNodesList, processor: Processor, ctx: ProcessingContext, mode: ValType): NodeNs {
        val ns = processor.computeString(node.nodes.first(), ctx)
        val new = NodeNs(node.info.withType(NS_), node.nodes.drop(1).toMutableList(), ns)
        processNodesList(new, processor, ctx.with(ctx.global.with(ns)), mode)
        return new
    }
}