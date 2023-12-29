package ru.DmN.pht.std.ast

import ru.DmN.siberia.ast.Node
import ru.DmN.siberia.ast.NodeNodesList
import ru.DmN.siberia.node.INodeInfo

class NodeLazySymbol(info: INodeInfo, nodes: MutableList<Node>, var symbol: String?) : NodeNodesList(info, nodes) {
    override fun copy(): NodeLazySymbol =
        NodeLazySymbol(info, copyNodes(), symbol)
}