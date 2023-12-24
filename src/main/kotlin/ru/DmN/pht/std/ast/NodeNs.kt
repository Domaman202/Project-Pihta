package ru.DmN.pht.std.ast

import ru.DmN.pht.std.utils.text
import ru.DmN.siberia.ast.Node
import ru.DmN.siberia.ast.NodeNodesList
import ru.DmN.siberia.node.INodeInfo
import ru.DmN.siberia.utils.indent

class NodeNs(info: INodeInfo, nodes: MutableList<Node>, val namespace: String) : NodeNodesList(info, nodes) {
    override fun copy(): NodeNs =
        NodeNs(info, copyNodes(), namespace)

    override fun print(builder: StringBuilder, indent: Int): StringBuilder =
        printNodes(builder.indent(indent).append('[').append(text).append(' ').append(namespace), indent).append(']')
}