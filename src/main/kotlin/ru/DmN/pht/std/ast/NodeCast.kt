package ru.DmN.pht.std.ast

import ru.DmN.pht.base.lexer.Token
import ru.DmN.pht.base.parser.ast.IValueNode
import ru.DmN.pht.base.parser.ast.Node

class NodeCast(tkOperation: Token, val to: String, val value: Node) : Node(tkOperation), IValueNode {
    override fun isConst(): Boolean = value.isConst()
}