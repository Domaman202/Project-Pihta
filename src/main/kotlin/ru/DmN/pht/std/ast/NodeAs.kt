package ru.DmN.pht.std.ast

import ru.DmN.siberia.ast.Node
import ru.DmN.siberia.ast.NodeNodesList
import ru.DmN.siberia.lexer.Token
import ru.DmN.siberia.utils.VirtualType
import ru.DmN.siberia.utils.indent

class NodeAs(tkOperation: Token, nodes: MutableList<Node>, val type: VirtualType) : NodeNodesList(tkOperation, nodes) {
    override fun copy(): NodeAs =
        NodeAs(token, copyNodes(), type)

    override fun print(builder: StringBuilder, indent: Int): StringBuilder =
        printNodes(builder.indent(indent).append('[').append(token.text).append('\n').indent(indent + 1).append("type = ").append(type.name), indent).append(']')
}