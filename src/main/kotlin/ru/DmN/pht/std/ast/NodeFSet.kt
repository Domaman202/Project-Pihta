package ru.DmN.pht.std.ast

import ru.DmN.siberia.lexer.Token
import ru.DmN.siberia.ast.Node
import ru.DmN.siberia.ast.NodeNodesList
import ru.DmN.siberia.utils.VirtualType
import ru.DmN.siberia.utils.indent

class NodeFSet(tkOperation: Token, nodes: MutableList<Node>, val name: String, val type: Type, val vtype: VirtualType) : NodeNodesList(tkOperation, nodes) {
    override fun copy(): NodeFSet =
        NodeFSet(token, copyNodes(), name, type, vtype)

    override fun print(builder: StringBuilder, indent: Int): StringBuilder =
        printNodes(builder.indent(indent).append('[').append(token.text).append(" (").append(type).append(")\n").indent(indent + 1).append("name = ").append(name), indent).append(']')

    enum class Type {
        UNKNOWN,
        STATIC,
        INSTANCE
    }
}