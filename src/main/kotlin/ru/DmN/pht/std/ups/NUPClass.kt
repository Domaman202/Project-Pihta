package ru.DmN.pht.std.ups

import ru.DmN.siberia.Processor
import ru.DmN.siberia.Unparser
import ru.DmN.siberia.processor.ctx.ProcessingContext
import ru.DmN.siberia.unparser.UnparsingContext
import ru.DmN.siberia.unparsers.NUDefault
import ru.DmN.siberia.utils.VirtualType
import ru.DmN.pht.std.ast.NodeType
import ru.DmN.siberia.utils.INUP
import ru.DmN.pht.std.unparsers.NUDefaultX

object NUPClass : INUP<NodeType, NodeType> {
    override fun unparse(node: NodeType, unparser: Unparser, ctx: UnparsingContext, indent: Int) {
        unparser.out.apply {
            append('(').append(NUDefaultX.text(node.token)).append(' ').append(node.type.simpleName).append(" [")
            node.type.parents.forEachIndexed { i, it ->
                append('^').append(it.name)
                if (node.type.parents.size + 1 < i) {
                    append(' ')
                }
            }
            append(']')
            NUDefault.unparseNodes(node, unparser, ctx, indent)
            append(')')
        }
    }

    override fun calc(node: NodeType, processor: Processor, ctx: ProcessingContext): VirtualType? =
        if (node.token.text == "!obj")
            node.type
        else null
}