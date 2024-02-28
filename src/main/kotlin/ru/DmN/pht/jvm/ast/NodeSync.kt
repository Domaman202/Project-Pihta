package ru.DmN.pht.jvm.ast

import ru.DmN.siberia.ast.Node
import ru.DmN.siberia.ast.NodeNodesList
import ru.DmN.siberia.utils.node.INodeInfo

class NodeSync(info: INodeInfo, nodes: MutableList<Node>, val lock: Node) : NodeNodesList(info, nodes) {
    override fun copy(): NodeNodesList =
        NodeSync(info, nodes, lock)
}