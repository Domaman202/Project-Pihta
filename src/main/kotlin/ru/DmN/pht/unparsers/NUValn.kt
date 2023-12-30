package ru.DmN.pht.unparsers

import ru.DmN.siberia.Unparser
import ru.DmN.siberia.ast.NodeNodesList
import ru.DmN.siberia.unparser.UnparsingContext
import ru.DmN.siberia.unparsers.INodeUnparser
import ru.DmN.siberia.unparsers.NUDefault

object NUValn : INodeUnparser<NodeNodesList> {
    override fun unparse(node: NodeNodesList, unparser: Unparser, ctx: UnparsingContext, indent: Int) {
        unparser.out.apply {
            append('[')
            if (node.nodes.isNotEmpty()) {
                NUDefault.unparseNodes(node, unparser, ctx, indent)
                append('\n').append("\t".repeat(indent))
            }
            append(']')
        }
    }
}