package ru.DmN.pht.std.ast

import ru.DmN.pht.base.ast.Node
import ru.DmN.pht.base.ast.NodeNodesList
import ru.DmN.pht.base.lexer.Token
import ru.DmN.pht.base.utils.VirtualType
import ru.DmN.pht.base.utils.indent

class NodeAs(tkOperation: Token, nodes: MutableList<Node>, val type: VirtualType) : NodeNodesList(tkOperation, nodes) {
    override fun copy(): NodeAs =
        NodeAs(token, copyNodes(), type)

    override fun print(builder: StringBuilder, indent: Int): StringBuilder =
        printNodes(builder.indent(indent).append('[').append(token.text).append('\n').indent(indent + 1).append("type = ").append(type.name), indent).append(']')
}