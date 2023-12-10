package ru.DmN.phtx.pcl.ast

import ru.DmN.siberia.lexer.Token
import ru.DmN.siberia.utils.indent

class NodeValue(token: Token, offset: Int, name: String, val type: Type, val value: String) : NodeElement(token, offset, name) {
    override fun print(builder: StringBuilder, indent: Int): StringBuilder =
        builder.indent(indent).append('(').append(token.line).append(',').append(offset).append(") ").append(name).append('\n')
            .indent(indent).append('[').append(type).append("] ").append(value)

    enum class Type {
        NUMBER,
        STRING
    }
}