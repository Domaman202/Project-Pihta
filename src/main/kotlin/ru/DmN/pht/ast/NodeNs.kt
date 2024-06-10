package ru.DmN.pht.ast

import ru.DmN.siberia.ast.Node
import ru.DmN.siberia.ast.NodeNodesList
import ru.DmN.siberia.utils.indent
import ru.DmN.siberia.utils.node.INodeInfo

class NodeNs(
    info: INodeInfo,
    nodes: MutableList<Node>,
    val namespace: String
) : NodeNodesList(info, nodes) {
    override fun copy(): NodeNs =
        NodeNs(info, copyNodes(), namespace)

    override fun print(builder: StringBuilder, indent: Int, short: Boolean): StringBuilder = builder.apply {
        indent(indent).append('[').append(info.type).append('\n')
            .indent(indent + 1).append("(namespace = ").append(namespace).append(')')
        printNodes(this, indent, short).append(']')
    }
}