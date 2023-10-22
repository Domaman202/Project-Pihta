package ru.DmN.pht.std.unparsers

import ru.DmN.pht.base.Unparser
import ru.DmN.pht.base.ast.NodeNodesList
import ru.DmN.pht.base.lexer.Token
import ru.DmN.pht.base.unparser.UnparsingContext
import ru.DmN.pht.base.unparsers.INodeUnparser
import ru.DmN.pht.base.unparsers.NUDefault

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