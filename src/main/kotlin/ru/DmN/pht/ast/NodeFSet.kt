package ru.DmN.pht.ast

import ru.DmN.siberia.ast.Node
import ru.DmN.siberia.ast.NodeNodesList
import ru.DmN.siberia.utils.node.INodeInfo
import ru.DmN.siberia.utils.vtype.VirtualField

class NodeFSet(info: INodeInfo, nodes: MutableList<Node>, val field: VirtualField) : NodeNodesList(info, nodes) {
    override fun copy(): NodeFSet =
        NodeFSet(info, copyNodes(), field)
}