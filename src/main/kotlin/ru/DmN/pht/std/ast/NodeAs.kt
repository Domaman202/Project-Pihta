package ru.DmN.pht.std.ast

import ru.DmN.pht.std.utils.text
import ru.DmN.siberia.ast.Node
import ru.DmN.siberia.ast.NodeNodesList
import ru.DmN.siberia.node.INodeInfo
import ru.DmN.siberia.utils.VirtualType
import ru.DmN.siberia.utils.indent

class NodeAs(info: INodeInfo, nodes: MutableList<Node>, val type: VirtualType) : NodeNodesList(info, nodes) {
    override fun copy(): NodeAs =
        NodeAs(info, copyNodes(), type)

    override fun print(builder: StringBuilder, indent: Int): StringBuilder =
        printNodes(builder.indent(indent).append('[').append(text).append('\n').indent(indent + 1).append("type = ").append(type.name), indent).append(']')
}