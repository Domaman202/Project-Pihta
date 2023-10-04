package ru.DmN.pht.std.base.processor.processors

import ru.DmN.pht.base.Processor
import ru.DmN.pht.base.parser.ast.NodeNodesList
import ru.DmN.pht.base.processor.utils.ProcessingContext
import ru.DmN.pht.base.processor.processors.NRDefault
import ru.DmN.pht.base.processor.utils.ValType
import ru.DmN.pht.std.base.ast.NodeNs
import ru.DmN.pht.std.base.processor.utils.global
import ru.DmN.pht.std.base.processor.utils.with
import ru.DmN.pht.std.base.utils.computeString

object NRSubNs : IStdNodeProcessor<NodeNodesList> {
    override fun process(node: NodeNodesList, processor: Processor, ctx: ProcessingContext, mode: ValType): NodeNs {
        val gctx = ctx.global
        val ns = processor.computeString(node.nodes.first(), ctx)
        val new = NodeNs(node.tkOperation, node.nodes.drop(1).toMutableList(), ns)
        NRDefault.process(new, processor, ctx.with(gctx.with("${gctx.namespace}${ns}")), mode)
        return new
    }
}