package ru.DmN.pht.ast

import ru.DmN.siberia.ast.Node
import ru.DmN.siberia.ast.NodeNodesList
import ru.DmN.siberia.utils.node.INodeInfo
import ru.DmN.siberia.utils.vtype.VirtualType

class NodeFGet(
    info: INodeInfo,
    nodes: MutableList<Node>,
    val name: String,
    val type: Type,
    val vtype: VirtualType
) : NodeNodesList(info, nodes) {
    override fun copy(): NodeFGet =
        NodeFGet(info, copyNodes(), name, type, vtype)

    enum class Type {
        UNKNOWN,
        STATIC,
        INSTANCE
    }
}