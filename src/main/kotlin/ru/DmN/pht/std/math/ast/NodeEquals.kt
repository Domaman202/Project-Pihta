package ru.DmN.pht.std.math.ast

import ru.DmN.pht.base.lexer.Token
import ru.DmN.pht.base.parser.ast.Node
import ru.DmN.pht.base.parser.ast.NodeNodesList

class NodeEquals(tkOperation: Token, val operation: Operation, nodes: MutableList<Node>) : NodeNodesList(tkOperation, nodes) {
    enum class Operation {
        EQ,
        NE,
        LT,
        LE,
        GT,
        GE,
    }
}