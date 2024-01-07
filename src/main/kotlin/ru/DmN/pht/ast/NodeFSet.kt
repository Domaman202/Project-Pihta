package ru.DmN.pht.std.ast

import ru.DmN.siberia.ast.Node
import ru.DmN.siberia.ast.NodeNodesList
import ru.DmN.siberia.node.INodeInfo
import ru.DmN.siberia.utils.VirtualField
import ru.DmN.siberia.utils.VirtualType

class NodeFSet(info: INodeInfo, nodes: MutableList<Node>, val field: VirtualField) : NodeNodesList(info, nodes) {
    override fun copy(): NodeFSet =
        NodeFSet(info, copyNodes(), field)
}