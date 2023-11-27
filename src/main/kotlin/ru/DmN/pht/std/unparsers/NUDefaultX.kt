package ru.DmN.pht.std.unparsers

import ru.DmN.siberia.Unparser
import ru.DmN.siberia.ast.NodeNodesList
import ru.DmN.siberia.lexer.Token
import ru.DmN.siberia.unparser.UnparsingContext
import ru.DmN.siberia.unparsers.INodeUnparser
import ru.DmN.siberia.unparsers.NUDefault

object NUDefaultX : INodeUnparser<NodeNodesList> {
    override fun unparse(node: NodeNodesList, unparser: Unparser, ctx: UnparsingContext, indent: Int) {
        unparser.out.run {
            append('(').append(text(node.token))
            if (node.nodes.isNotEmpty())
                append(' ')
            NUDefault.unparseNodes(node, unparser, ctx, indent)
            append(')')
        }
    }

    fun text(token: Token) =
        token.text!!.let { if (it.startsWith('!') && it.length > 1) it.substring(1) else it }
}