package ru.DmN.pht.std.ast

import ru.DmN.siberia.ast.Node
import ru.DmN.siberia.ast.NodeNodesList
import ru.DmN.siberia.node.INodeInfo
import ru.DmN.siberia.utils.VirtualType

class NodeFGet(info: INodeInfo, nodes: MutableList<Node>, val name: String, val type: Type, val vtype: VirtualType) : NodeNodesList(info, nodes) {
    override fun copy(): NodeFGet =
        NodeFGet(info, copyNodes(), name, type, vtype)

    init {
        if (name == "INSTANCE")
            println()
    }

//    override fun print(builder: StringBuilder, indent: Int): StringBuilder =
//        printNodes(builder.indent(indent).append('[').append(text).append(" (").append(type).append(")\n").indent(indent + 1).append("name = ").append(name), indent).append(']')

    enum class Type {
        UNKNOWN,
        STATIC,
        INSTANCE
    }
}