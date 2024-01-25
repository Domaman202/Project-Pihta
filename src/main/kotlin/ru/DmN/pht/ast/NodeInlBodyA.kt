package ru.DmN.pht.ast

import ru.DmN.siberia.ast.Node
import ru.DmN.siberia.ast.NodeNodesList
import ru.DmN.siberia.node.INodeInfo
import ru.DmN.siberia.utils.VirtualType

open class NodeInlBodyA(info: INodeInfo, nodes: MutableList<Node>, var type: VirtualType?) : NodeNodesList(info, nodes) {
    override fun copy(): NodeInlBodyA =
        NodeInlBodyA(info, copyNodes(), type)
}