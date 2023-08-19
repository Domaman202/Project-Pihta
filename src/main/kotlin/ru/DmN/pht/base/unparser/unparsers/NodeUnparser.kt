package ru.DmN.pht.base.unparser.unparsers

import ru.DmN.pht.base.Unparser
import ru.DmN.pht.base.parser.ast.Node
import ru.DmN.pht.base.unparser.UnparsingContext


open class NodeUnparser<T : Node> {
    open fun unparse(unparser: Unparser, ctx: UnparsingContext, node: T) {}
}