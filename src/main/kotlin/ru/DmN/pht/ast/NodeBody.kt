package ru.DmN.pht.ast

import ru.DmN.pht.processor.ctx.BodyContext
import ru.DmN.siberia.ast.Node
import ru.DmN.siberia.ast.NodeNodesList
import ru.DmN.siberia.utils.node.INodeInfo

class NodeBody(info: INodeInfo, nodes: MutableList<Node>, var ctx: BodyContext? = null) : NodeNodesList(info, nodes) {
    override fun copy(): NodeBody =
        NodeBody(info, copyNodes(), ctx?.copy())
}