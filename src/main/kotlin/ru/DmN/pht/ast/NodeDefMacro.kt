package ru.DmN.pht.ast

import ru.DmN.siberia.ast.Node
import ru.DmN.siberia.ast.NodeNodesList
import ru.DmN.siberia.utils.node.INodeInfo
import java.util.*

class NodeDefMacro(info: INodeInfo, nodes: MutableList<Node>, val uuid: UUID) : NodeNodesList(info, nodes) {
    override fun copy(): NodeDefMacro =
        NodeDefMacro(info, copyNodes(), uuid)
}