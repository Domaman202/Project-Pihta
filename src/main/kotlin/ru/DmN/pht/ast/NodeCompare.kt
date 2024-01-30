package ru.DmN.pht.ast

import ru.DmN.siberia.ast.Node
import ru.DmN.siberia.ast.NodeNodesList
import ru.DmN.siberia.node.INodeInfo

class NodeCompare(info: INodeInfo, nodes: MutableList<Node>) : NodeNodesList(info, nodes) {
    override fun copy(): NodeCompare =
        NodeCompare(info, copyNodes())
}