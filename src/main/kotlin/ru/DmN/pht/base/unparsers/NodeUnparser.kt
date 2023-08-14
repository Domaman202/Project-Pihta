package ru.DmN.pht.base.unparsers

import ru.DmN.pht.base.Unparser
import ru.DmN.pht.base.parser.ast.Node


open class NodeUnparser<T : Node> {
    open fun unparse(unparser: Unparser, node: T) {}
}