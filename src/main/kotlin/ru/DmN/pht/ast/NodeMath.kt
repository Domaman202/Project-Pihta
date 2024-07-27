package ru.DmN.pht.ast

import ru.DmN.siberia.ast.Node
import ru.DmN.siberia.ast.NodeNodesList
import ru.DmN.siberia.utils.indent
import ru.DmN.siberia.utils.node.INodeInfo
import ru.DmN.siberia.utils.vtype.VirtualType

class NodeMath(
    info: INodeInfo,
    nodes: MutableList<Node>,
    val type: VirtualType
) : NodeNodesList(info, nodes) {
    override fun copy(): NodeMath =
        NodeMath(info, copyNodes(), type)

    override fun print(builder: StringBuilder, indent: Int): StringBuilder = builder.apply {
        indent(indent).append('[').append(info.type).append('\n')
        indent(indent + 1).append("(type = ").append(type).append(')')
        printNodes(builder, indent)
        append(']')
    }
}