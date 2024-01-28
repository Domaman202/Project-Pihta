package ru.DmN.pht.std.ast

import ru.DmN.siberia.ast.Node
import ru.DmN.siberia.ast.NodeNodesList
import ru.DmN.siberia.node.INodeInfo

class NodeFieldSet(info: INodeInfo, nodes: MutableList<Node>, val instance: Node, val name: String, val static: Boolean, val native: Boolean = false) : NodeNodesList(info, nodes) {
    override fun copy(): NodeFieldSet =
        NodeFieldSet(info, copyNodes(), instance, name, static, native)
}