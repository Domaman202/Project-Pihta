package ru.DmN.pht.std.ups

import ru.DmN.pht.std.ast.NodeIncDec
import ru.DmN.pht.std.unparsers.NUDefaultX
import ru.DmN.siberia.Processor
import ru.DmN.siberia.Unparser
import ru.DmN.siberia.processor.ctx.ProcessingContext
import ru.DmN.siberia.unparser.UnparsingContext
import ru.DmN.siberia.utils.INUP
import ru.DmN.siberia.utils.VirtualType

object NUPIncDecB : INUP<NodeIncDec, NodeIncDec> {
    override fun unparse(node: NodeIncDec, unparser: Unparser, ctx: UnparsingContext, indent: Int) {
        unparser.out.append('(').append(NUDefaultX.text(node.token).let { if (node.postfix) "$it-" else it }).append(' ').append(node.name).append(')')
    }

    override fun calc(node: NodeIncDec, processor: Processor, ctx: ProcessingContext): VirtualType? =
        NUPGetOrName.calc(node.name, ctx)
}