package ru.DmN.pht.jvm.ast

import ru.DmN.siberia.ast.Node
import ru.DmN.siberia.ast.NodeNodesList
import ru.DmN.siberia.node.INodeInfo
import ru.DmN.siberia.utils.VirtualType

class NodeAnnotation(info: INodeInfo, nodes: MutableList<Node>, val type: VirtualType) : NodeNodesList(info, nodes) {
    override fun copy(): NodeAnnotation =
        NodeAnnotation(info, copyNodes(), type)
}