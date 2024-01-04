package ru.DmN.pht.std.ast

import ru.DmN.siberia.ast.Node
import ru.DmN.siberia.ast.NodeNodesList
import ru.DmN.siberia.node.INodeInfo
import ru.DmN.siberia.utils.VirtualType
import ru.DmN.siberia.utils.indent

class NodeIsAs(info: INodeInfo, nodes: MutableList<Node>, val type: VirtualType) : NodeNodesList(info, nodes) {
    override fun copy(): NodeIsAs =
        NodeIsAs(info, copyNodes(), type)

    override fun print(builder: StringBuilder, indent: Int, short: Boolean): StringBuilder =
        printNodes(builder.indent(indent).append('[').append(info.type).append('\n').indent(indent + 1).append("(type = ").append(type).append(')'), indent, short).append(']')
}