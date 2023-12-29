package ru.DmN.pht.std.processors

import ru.DmN.pht.std.ast.NodeNs
import ru.DmN.pht.std.node.NodeTypes
import ru.DmN.pht.std.processor.utils.global
import ru.DmN.pht.std.processor.utils.with
import ru.DmN.pht.std.utils.computeString
import ru.DmN.siberia.Processor
import ru.DmN.siberia.ast.NodeNodesList
import ru.DmN.siberia.processor.ctx.ProcessingContext
import ru.DmN.siberia.processor.utils.ValType
import ru.DmN.siberia.processor.utils.processNodesList
import ru.DmN.siberia.processors.INodeProcessor

object NRNs : INodeProcessor<NodeNodesList> { // todo: calc
    override fun process(node: NodeNodesList, processor: Processor, ctx: ProcessingContext, mode: ValType): NodeNs {
        val ns = processor.computeString(node.nodes.first(), ctx)
        val new = NodeNs(node.info.withType(NodeTypes.NS_), node.nodes.drop(1).toMutableList(), ns)
        processNodesList(new, processor, ctx.with(ctx.global.with(ns)), mode)
        return new
    }
}