package ru.DmN.pht.ast

import ru.DmN.siberia.ast.Node
import ru.DmN.siberia.ast.NodeNodesList
import ru.DmN.siberia.node.INodeInfo
import ru.DmN.siberia.parser.ctx.ParsingContext

class NodeIncPht(info: INodeInfo, nodes: MutableList<Node>, val ctx: ParsingContext) : NodeNodesList(info, nodes) {
    override fun copy(): NodeIncPht =
        NodeIncPht(info, copyNodes(), ctx)
}