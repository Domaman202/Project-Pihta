package ru.DmN.pht.std.ast

import ru.DmN.pht.base.ast.Node
import ru.DmN.pht.base.lexer.Token
import ru.DmN.pht.base.utils.indent

class NodeIncDec(tkOperation: Token, val name: String) : Node(tkOperation) {
    override fun print(builder: StringBuilder, indent: Int): StringBuilder =
        builder.indent(indent).append('[').append(token.text).append(' ').append(name).append(']')
}