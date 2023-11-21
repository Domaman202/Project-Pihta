package ru.DmN.pht.std.processors

import ru.DmN.pht.base.Processor
import ru.DmN.pht.base.ast.NodeNodesList
import ru.DmN.pht.base.processor.utils.ProcessingContext
import ru.DmN.pht.base.processor.utils.ValType
import ru.DmN.pht.base.processors.INodeProcessor
import ru.DmN.pht.base.processors.NRDefault
import ru.DmN.pht.std.ast.NodeNs
import ru.DmN.pht.std.processor.utils.global
import ru.DmN.pht.std.processor.utils.with
import ru.DmN.pht.std.utils.computeString

object NRNs : INodeProcessor<NodeNodesList> {
    override fun process(node: NodeNodesList, processor: Processor, ctx: ProcessingContext, mode: ValType): NodeNs {
        val ns = processor.computeString(node.nodes.first(), ctx)
        val new = NodeNs(node.token.processed(), node.nodes.drop(1).toMutableList(), ns)
        NRDefault.process(new, processor, ctx.with(ctx.global.with(ns)), mode)
        return new
    }
}