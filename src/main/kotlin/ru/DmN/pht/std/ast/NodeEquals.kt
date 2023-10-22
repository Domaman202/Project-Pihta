package ru.DmN.pht.std.ast

import ru.DmN.pht.base.lexer.Token
import ru.DmN.pht.base.ast.Node
import ru.DmN.pht.base.ast.NodeNodesList

class NodeEquals(tkOperation: Token, nodes: MutableList<Node>, val operation: Operation) : NodeNodesList(tkOperation, nodes) {
    override fun copy(): NodeEquals =
        NodeEquals(token, copyNodes(), operation)

    enum class Operation {
        EQ,
        NE,
        LT,
        LE,
        GT,
        GE,
    }
}