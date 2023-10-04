package ru.DmN.pht.std.math.ast

import ru.DmN.pht.base.lexer.Token
import ru.DmN.pht.base.parser.ast.Node
import ru.DmN.pht.base.parser.ast.NodeNodesList

/**
 * Math N-Arguments Node
 */
class NodeMath(tkOperation: Token, val operation: Operation, nodes: MutableList<Node>) : NodeNodesList(tkOperation, nodes) {
    enum class Operation {
        ADD,
        SUB,
        MUL,
        DIV,
    }
}