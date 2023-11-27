package ru.DmN.pht.std.ast

import ru.DmN.siberia.lexer.Token
import ru.DmN.siberia.ast.Node
import ru.DmN.siberia.utils.indent

open class NodeGetOrName(token: Token, val name: String, val static: Boolean) : Node(token), IValueNode {
    override fun isLiteral(): Boolean =
        true

    override fun getValueAsString(): String =
        name

    override fun print(builder: StringBuilder, indent: Int): StringBuilder =
        builder.indent(indent).append('[').append(token.text).append(']').append(if (static) " (static) " else " (nostatic) ").append(name)
}