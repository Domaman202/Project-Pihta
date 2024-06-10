package ru.DmN.pht.ast

import ru.DmN.siberia.ast.Node
import ru.DmN.siberia.ast.NodeNodesList
import ru.DmN.siberia.utils.node.INodeInfo

class NodeFMGet(
    info: INodeInfo,
    nodes: MutableList<Node>,
    val instance: Node,
    val name: String,
    val static: Boolean,
    val native: Boolean = false
) : NodeNodesList(info, nodes) {
    override fun copy(): NodeFMGet =
        NodeFMGet(info, copyNodes(), instance, name, static, native)
}