package ru.DmN.pht.std.ups

import ru.DmN.pht.std.ast.NodeNamedList
import ru.DmN.pht.std.unparsers.NUDefaultX
import ru.DmN.siberia.Processor
import ru.DmN.siberia.Unparser
import ru.DmN.siberia.ast.Node
import ru.DmN.siberia.processor.ctx.ProcessingContext
import ru.DmN.siberia.processor.utils.ValType
import ru.DmN.siberia.processors.NRDefault
import ru.DmN.siberia.unparser.UnparsingContext
import ru.DmN.siberia.unparsers.NUDefault
import ru.DmN.siberia.utils.INUP
import ru.DmN.siberia.utils.VirtualType

object NUPNamedList : INUP<NodeNamedList, NodeNamedList> {
    override fun unparse(node: NodeNamedList, unparser: Unparser, ctx: UnparsingContext, indent: Int) {
        unparser.out.apply {
            append('(').append(NUDefaultX.text(node.token)).append(' ').append(node.name)
            if (node.nodes.isNotEmpty())
                append(' ')
            NUDefault.unparseNodes(node, unparser, ctx, indent)
            append(')')
        }
    }

    override fun calc(node: NodeNamedList, processor: Processor, ctx: ProcessingContext): VirtualType? =
        NRDefault.calc(node, processor, ctx)

    override fun process(node: NodeNamedList, processor: Processor, ctx: ProcessingContext, mode: ValType): Node? =
        NRDefault.process(node, processor, ctx, mode)
}