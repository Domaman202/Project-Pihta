package ru.DmN.phtx.pcl.ast

import ru.DmN.siberia.lexer.Token

class NodeValue(token: Token, offset: Int, val type: Type, val value: String) : NodeElement(token, offset) {
    override fun printWLAF(builder: StringBuilder, indent: Int): StringBuilder =
        builder.append('[').append(type).append("] ").append(value)

    enum class Type {
        BON,
        STRING
    }
}