package ru.DmN.pht.ast

import ru.DmN.siberia.ast.Node
import ru.DmN.siberia.ast.NodeNodesList
import ru.DmN.siberia.node.INodeInfo
import ru.DmN.siberia.utils.indent

class NodeSet(info: INodeInfo, nodes: MutableList<Node>, val name: String) : NodeNodesList(info, nodes) {
    override fun copy(): NodeSet =
        NodeSet(info, copyNodes(), name)

    override fun print(builder: StringBuilder, indent: Int, short: Boolean): StringBuilder =
        printNodes(builder.indent(indent).append('[').append(info.type).append('\n').indent(indent + 1).append("(name = ").append(name).append(')'), indent, short).append(']')
}