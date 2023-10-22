package ru.DmN.pht.std.ast

import ru.DmN.pht.base.lexer.Token
import ru.DmN.pht.base.ast.Node
import ru.DmN.pht.base.ast.NodeNodesList

/**
 * Math N-Arguments Node
 */
class NodeMath(tkOperation: Token, nodes: MutableList<Node>, val operation: Operation) : NodeNodesList(tkOperation, nodes) {
    override fun copy(): NodeMath =
        NodeMath(token, copyNodes(), operation)

    enum class Operation {
        PLUS,
        MINUS,
        MUL,
        DIV,
    }
}