package ru.DmN.pht.ast

import ru.DmN.siberia.ast.Node
import ru.DmN.siberia.ast.NodeNodesList
import ru.DmN.siberia.parser.ctx.ParsingContext
import ru.DmN.siberia.utils.node.INodeInfo

class NodeIncPht(info: INodeInfo, nodes: MutableList<Node>, val ctx: ParsingContext) : NodeNodesList(info, nodes) {
    override fun copy(): NodeIncPht =
        NodeIncPht(info, copyNodes(), ctx)
}