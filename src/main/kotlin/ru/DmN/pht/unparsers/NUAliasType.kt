package ru.DmN.pht.unparsers

import ru.DmN.pht.imports.ast.NodeAlias
import ru.DmN.siberia.unparser.Unparser
import ru.DmN.siberia.unparser.ctx.UnparsingContext
import ru.DmN.siberia.unparsers.INodeUnparser
import ru.DmN.siberia.utils.operation

object NUAliasType : INodeUnparser<NodeAlias> {
    override fun unparse(node: NodeAlias, unparser: Unparser, ctx: UnparsingContext, indent: Int) {
        unparser.out.apply {
            append('(').append(node.operation).append(" [")
            node.imports.forEach {
                append('\n').append("\t".repeat(indent + 1))
                    .append('[').append(it.first).append(' ').append(it.second).append(']')
            }
            append("])")
        }
    }
}