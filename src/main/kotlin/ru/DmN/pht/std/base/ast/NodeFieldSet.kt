package ru.DmN.pht.std.base.ast

import ru.DmN.pht.base.lexer.Token
import ru.DmN.pht.base.parser.ast.Node

class NodeFieldSet(tkOperation: Token, val instance: Node, name: String, value: Node?, val static: Boolean) : NodeSet(tkOperation, name, value) {
    override val nodes: List<Node>
        get() = super.nodes + instance
}