package ru.DmN.pht.std.ast

import ru.DmN.siberia.ast.Node
import ru.DmN.siberia.ast.NodeNodesList
import ru.DmN.siberia.node.INodeInfo

class NodeFieldA(info: INodeInfo, nodes: MutableList<Node>, override var static: Boolean = false, override var final: Boolean = false) : NodeNodesList(info, nodes), IStaticallyNode, IFinallyNode {
    override fun copy(): NodeNodesList =
        NodeFieldA(info, copyNodes(), static, final)
}