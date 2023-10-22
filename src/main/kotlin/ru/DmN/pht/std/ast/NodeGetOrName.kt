package ru.DmN.pht.std.ast

import ru.DmN.pht.base.lexer.Token
import ru.DmN.pht.base.ast.Node
import ru.DmN.pht.base.utils.indent

open class NodeGetOrName(token: Token, val name: String, val static: Boolean) : Node(token) {
    override fun isLiteral(): Boolean =
        true

    override fun getValueAsString(): String =
        name

    override fun print(builder: StringBuilder, indent: Int): StringBuilder =
        builder.indent(indent).append('[').append(token.text).append(']').append(if (static) " (static) " else " (nostatic) ").append(name)
}