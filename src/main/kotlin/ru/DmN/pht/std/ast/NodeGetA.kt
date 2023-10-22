package ru.DmN.pht.std.ast

import ru.DmN.pht.base.lexer.Token
import ru.DmN.pht.base.ast.Node

class NodeGetA(tkOperation: Token, val name: String, val type: Type) : Node(tkOperation) {
    enum class Type {
        UNKNOWN,
        VARIABLE,
        THIS_FIELD,
        THIS_STATIC_FIELD
    }
}