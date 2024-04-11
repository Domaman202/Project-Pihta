package ru.DmN.pht.unparsers

import ru.DmN.siberia.unparser.Unparser
import ru.DmN.siberia.ast.Node

import ru.DmN.siberia.unparser.ctx.UnparsingContext
import ru.DmN.siberia.unparsers.INodeUnparser

object NUUnit : INodeUnparser<Node> {
    override fun unparse(node: Node, unparser: Unparser, ctx: UnparsingContext, indent: Int) {
        unparser.out.append("(unit)")
    }
}