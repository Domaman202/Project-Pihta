package ru.DmN.pht.ast

import ru.DmN.siberia.ast.Node
import ru.DmN.siberia.ast.NodeNodesList
import ru.DmN.siberia.utils.indent
import ru.DmN.siberia.utils.node.INodeInfo
import ru.DmN.siberia.utils.vtype.VirtualType

class NodeIsAs(info: INodeInfo, nodes: MutableList<Node>, val from: VirtualType, val type: VirtualType) : NodeNodesList(info, nodes) {
    override fun copy(): NodeIsAs =
        NodeIsAs(info, copyNodes(), from, type)

    override fun print(builder: StringBuilder, indent: Int, short: Boolean): StringBuilder =
        printNodes(builder.indent(indent).append('[').append(info.type).append('\n').indent(indent + 1).append("(type = ").append(type).append(')'), indent, short).append(']')
}