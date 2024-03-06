package ru.DmN.pht.unparsers

import ru.DmN.siberia.unparser.Unparser
import ru.DmN.siberia.ast.Node
import ru.DmN.siberia.unparser.UnparsingContext
import ru.DmN.siberia.unparsers.INodeUnparser

object NUDebug : INodeUnparser<Node> {
    override fun unparse(node: Node, unparser: Unparser, ctx: UnparsingContext, indent: Int) {
        unparser.out.append("(debug)")
    }
}