package ru.DmN.pht.std.math.ast

import ru.DmN.pht.base.lexer.Token
import ru.DmN.pht.base.parser.ast.Node

class NodeNot(tkOperation: Token, val value: Node) : Node(tkOperation) {
    override val nodes: List<Node>
        get() = listOf(value)
}