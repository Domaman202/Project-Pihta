package ru.DmN.pht.ast

import ru.DmN.siberia.ast.Node
import ru.DmN.siberia.ast.NodeNodesList
import ru.DmN.siberia.utils.node.INodeInfo

class NodeIfPlatform(info: INodeInfo, nodes: MutableList<Node>, val platforms: List<String>) : NodeNodesList(info, nodes) {
    override fun copy(): NodeIfPlatform =
        NodeIfPlatform(info, copyNodes(), platforms)
}