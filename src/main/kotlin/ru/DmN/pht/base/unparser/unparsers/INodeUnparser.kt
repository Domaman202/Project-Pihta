package ru.DmN.pht.base.unparser.unparsers

import ru.DmN.pht.base.Unparser
import ru.DmN.pht.base.parser.ast.Node
import ru.DmN.pht.base.unparser.UnparsingContext


interface INodeUnparser<T : Node> {
    fun unparse(node: T, unparser: Unparser, ctx: UnparsingContext, indent: Int)
}