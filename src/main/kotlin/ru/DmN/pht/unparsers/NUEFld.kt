package ru.DmN.pht.unparsers

import ru.DmN.pht.ast.NodeEField
import ru.DmN.siberia.unparser.Unparser
import ru.DmN.siberia.unparser.UnparsingContext
import ru.DmN.siberia.unparsers.INodeUnparser
import ru.DmN.siberia.utils.operation

object NUEFld : INodeUnparser<NodeEField> {
    override fun unparse(node: NodeEField, unparser: Unparser, ctx: UnparsingContext, indent: Int) {
        unparser.out.apply {
            append('(').append(node.operation).append(" [")
            node.fields.forEach { it ->
                append('\n').append("\t".repeat(indent + 1)).append('[').append(it.first)
                it.second.forEach { append(' '); unparser.unparse(it, ctx, indent + 1) }
                append(']')
            }
            append("])")
        }
    }
}