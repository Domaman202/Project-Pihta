package ru.DmN.pht.unparsers

import ru.DmN.pht.ast.NodeImport
import ru.DmN.siberia.unparser.Unparser

import ru.DmN.siberia.unparser.ctx.UnparsingContext
import ru.DmN.siberia.unparsers.INodeUnparser
import ru.DmN.siberia.utils.operation

object NUImport : INodeUnparser<NodeImport> {
    override fun unparse(node: NodeImport, unparser: Unparser, ctx: UnparsingContext, indent: Int) {
        unparser.out.apply {
            append('(').append(node.operation).append(" \"").append(node.module).append("\" [")
            node.data.forEach { (k, v) ->
                append('\n').append("\t".repeat(indent + 1)).append('[').append(k)
                v.forEach { append(" [").append(it).append(']') }
                append("]\n")
            }
            append("])")
        }
    }
}