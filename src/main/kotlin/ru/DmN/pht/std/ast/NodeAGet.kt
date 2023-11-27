package ru.DmN.pht.std.ast

import ru.DmN.siberia.lexer.Token
import ru.DmN.siberia.ast.Node
import ru.DmN.siberia.utils.indent

class NodeAGet(tkOperation: Token, val arr: Node, val index: Node) : Node(tkOperation) {
    override fun print(builder: StringBuilder, indent: Int): StringBuilder =
        index.print(arr.print(builder.indent(indent).append('[').append(token.text).append('\n'), indent + 1).append('\n'), indent + 1).append('\n').indent(indent).append(']')
}