package ru.DmN.pht.std.ast

import ru.DmN.siberia.lexer.Token
import ru.DmN.siberia.ast.Node
import ru.DmN.siberia.utils.indent

class NodeASet(tkOperation: Token, val arr: Node, val index: Node, val value: Node) : Node(tkOperation) {
    override fun print(builder: StringBuilder, indent: Int): StringBuilder =
        value.print(index.print(arr.print(builder.indent(indent).append('[').append(token.text).append('\n'), indent + 1).append('\n'), indent + 1).append('\n'), indent + 1).append('\n').indent(indent).append(']')
}