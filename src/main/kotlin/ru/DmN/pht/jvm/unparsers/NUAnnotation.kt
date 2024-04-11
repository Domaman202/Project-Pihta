package ru.DmN.pht.jvm.unparsers

import ru.DmN.pht.jvm.ast.NodeAnnotation
import ru.DmN.siberia.unparser.Unparser
import ru.DmN.siberia.unparser.ctx.UnparsingContext
import ru.DmN.siberia.unparsers.INodeUnparser
import ru.DmN.siberia.unparsers.NUDefault
import ru.DmN.siberia.utils.operation

object NUAnnotation : INodeUnparser<NodeAnnotation> {
    override fun unparse(node: NodeAnnotation, unparser: Unparser, ctx: UnparsingContext, indent: Int) {
        unparser.out.apply {
            append('(').append(node.operation).append(" ^").append(node.type!!.name)
                .append(" [")
            node.arguments.forEach { (k, v) ->
                if (k == null) {
                    unparser.unparse(v, ctx, 0)
                } else {
                    append('[').append(k).append(' ')
                    unparser.unparse(v, ctx, 0)
                    append(']')
                }
            }
            append("] ")
            NUDefault.unparseNodes(node, unparser, ctx, indent)
            append(')')
        }
    }
}