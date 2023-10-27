package ru.DmN.pht.std.ast

import ru.DmN.pht.base.lexer.Token
import ru.DmN.pht.base.ast.Node
import ru.DmN.pht.base.ast.NodeNodesList
import ru.DmN.pht.base.utils.VirtualType
import ru.DmN.pht.base.utils.indent

class NodeFGet(tkOperation: Token, nodes: MutableList<Node>, val name: String, val type: Type, val vtype: VirtualType) : NodeNodesList(tkOperation, nodes) {
    override fun copy(): NodeFGet =
        NodeFGet(token, copyNodes(), name, type, vtype)

    override fun print(builder: StringBuilder, indent: Int): StringBuilder =
        printNodes(builder.indent(indent).append('[').append(token.text).append(" (").append(type).append(")\n").indent(indent + 1).append("name = ").append(name), indent).append(']')

    enum class Type {
        UNKNOWN,
        STATIC,
        INSTANCE
    }
}