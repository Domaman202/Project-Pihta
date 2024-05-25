package ru.DmN.pht.ast

import ru.DmN.siberia.ast.Node
import ru.DmN.siberia.ast.NodeNodesList
import ru.DmN.siberia.utils.node.INodeInfo
import ru.DmN.siberia.utils.vtype.VirtualType

class NodeMath(info: INodeInfo, nodes: MutableList<Node>, val type: VirtualType) : NodeNodesList(info, nodes) {
    override fun copy(): NodeMath =
        NodeMath(info, copyNodes(), type)
}