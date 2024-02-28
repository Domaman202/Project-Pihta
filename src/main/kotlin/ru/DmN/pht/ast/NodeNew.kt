package ru.DmN.pht.ast

import ru.DmN.siberia.ast.Node
import ru.DmN.siberia.ast.NodeNodesList
import ru.DmN.siberia.utils.indent
import ru.DmN.siberia.utils.node.INodeInfo
import ru.DmN.siberia.utils.vtype.VirtualMethod
import ru.DmN.siberia.utils.vtype.VirtualType

class NodeNew(info: INodeInfo, nodes: MutableList<Node>, val type: VirtualType, val ctor: VirtualMethod) : NodeNodesList(info, nodes) {
    override fun copy(): NodeNew =
        NodeNew(info, copyNodes(), type, ctor)

    override fun print(builder: StringBuilder, indent: Int, short: Boolean): StringBuilder = builder.apply {
        indent(indent).append('[').append(info.type).append('\n')
            .indent(indent + 1).append("(type = ").append(type).append(')')
        if (!short)
            append('\n').indent(indent + 1).append("(ctor = ").append(ctor.desc).append(')')
        if (nodes.isEmpty())
            append('\n').indent(indent)
        else printNodes(builder, indent, short)
        append(']')
    }
}