package ru.DmN.siberia.unparsers

import ru.DmN.pht.base.Unparser
import ru.DmN.pht.base.ast.NodeNodesList
import ru.DmN.pht.base.unparser.UnparsingContext
import ru.DmN.pht.std.utils.text

object NUDefault : INodeUnparser<NodeNodesList> {
    override fun unparse(node: NodeNodesList, unparser: Unparser, ctx: UnparsingContext, indent: Int) {
        unparser.out.run {
            append('(').append(node.text)
            if (node.nodes.isNotEmpty())
                append(' ')
            unparseNodes(node, unparser, ctx, indent)
            append(')')
        }
    }

    fun unparseNodes(node: NodeNodesList, unparser: Unparser, ctx: UnparsingContext, indent: Int) {
        node.nodes.forEach { n ->
            unparser.out.append('\n').append("\t".repeat(indent + 1))
            unparser.unparse(n, ctx, indent + 1)
        }
    }
}