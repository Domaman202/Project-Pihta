package ru.DmN.pht.unparsers

import ru.DmN.pht.std.ast.NodeImport
import ru.DmN.siberia.Unparser
import ru.DmN.siberia.unparser.UnparsingContext
import ru.DmN.siberia.unparsers.INodeUnparser
import ru.DmN.siberia.utils.operation

object NUImport : INodeUnparser<NodeImport> {
    override fun unparse(node: NodeImport, unparser: Unparser, ctx: UnparsingContext, indent: Int) {
        unparser.out.apply {
            append('(').append(node.operation).append(' ').append(node.module)
            node.data.forEach { (k, v) ->
                v.forEach {
                    append('\n').append("\t".repeat(indent + 1)).append('(').append(k).append(' ').append(it).append(')')
                }
            }
            append(')')
        }
    }
}