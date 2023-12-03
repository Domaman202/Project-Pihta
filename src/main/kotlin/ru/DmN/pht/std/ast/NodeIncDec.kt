package ru.DmN.pht.std.ast

import ru.DmN.siberia.ast.Node
import ru.DmN.siberia.lexer.Token
import ru.DmN.siberia.utils.indent

class NodeIncDec(tkOperation: Token, val name: String, val postfix: Boolean) : Node(tkOperation) {
    override fun print(builder: StringBuilder, indent: Int): StringBuilder =
        builder.indent(indent).append('[').append(token.text).append(' ').append(name).append(']')
}