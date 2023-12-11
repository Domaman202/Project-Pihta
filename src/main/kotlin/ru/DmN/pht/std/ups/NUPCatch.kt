package ru.DmN.pht.std.ups

import ru.DmN.pht.std.ast.NodeCatch
import ru.DmN.siberia.Processor
import ru.DmN.siberia.Unparser
import ru.DmN.siberia.processor.ctx.ProcessingContext
import ru.DmN.siberia.processors.NRDefault
import ru.DmN.siberia.unparser.UnparsingContext
import ru.DmN.siberia.utils.INUP
import ru.DmN.siberia.utils.VirtualType

object NUPCatch : INUP<NodeCatch, NodeCatch> {
    override fun unparse(node: NodeCatch, unparser: Unparser, ctx: UnparsingContext, indent: Int) {
        TODO()
    }

    override fun calc(node: NodeCatch, processor: Processor, ctx: ProcessingContext): VirtualType? =
        NRDefault.calc(node, processor, ctx)
}