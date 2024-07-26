package ru.DmN.pht.ast

import ru.DmN.siberia.ast.Node
import ru.DmN.siberia.ast.NodeNodesList
import ru.DmN.siberia.utils.indent
import ru.DmN.siberia.utils.node.INodeInfo

class NodeNamedList(
    info: INodeInfo,
    nodes: MutableList<Node>,
    val name: String
) : NodeNodesList(info, nodes) {
    override fun copy(): NodeNamedList =
        NodeNamedList(info, copyNodes(), name)

    override fun print(builder: StringBuilder, indent: Int): StringBuilder = builder.apply {
        indent(indent).append('[').append(info.type).append('\n')
        indent(indent + 1).append("(name = ").append(name).append(')')
        printNodes(builder, indent)
        append(']')
    }
}