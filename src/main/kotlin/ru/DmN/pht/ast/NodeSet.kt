package ru.DmN.pht.std.ast

import ru.DmN.siberia.ast.Node
import ru.DmN.siberia.ast.NodeNodesList
import ru.DmN.siberia.node.INodeInfo

class NodeSet(info: INodeInfo, nodes: MutableList<Node>, val name: String) : NodeNodesList(info, nodes) {
    override fun copy(): NodeSet =
        NodeSet(info, copyNodes(), name)
}