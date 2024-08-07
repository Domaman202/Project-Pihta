package ru.DmN.pht.ast

import ru.DmN.siberia.ast.Node
import ru.DmN.siberia.ast.NodeNodesList
import ru.DmN.siberia.utils.indent
import ru.DmN.siberia.utils.node.INodeInfo
import ru.DmN.siberia.utils.vtype.VirtualType

class NodeFGet(
    info: INodeInfo,
    nodes: MutableList<Node>,
    val name: String,
    val type: Type,
    val vtype: VirtualType
) : NodeNodesList(info, nodes) {
    override fun copy(): NodeFGet =
        NodeFGet(info, copyNodes(), name, type, vtype)

    override fun print(builder: StringBuilder, indent: Int): StringBuilder = builder.apply {
        indent(indent).append('[').append(info.type).append('\n')
        indent(indent + 1).append("(").append(type).append(")\n")
        indent(indent + 1).append("(name = ").append(name).append(")\n")
        indent(indent + 1).append("(type = ").append(vtype).append(")\n")
        indent(indent).append(']')
    }

    enum class Type {
        UNKNOWN,
        STATIC,
        INSTANCE
    }
}