package ru.DmN.pht.std.ast

import ru.DmN.pht.base.lexer.Token
import ru.DmN.pht.base.ast.Node
import ru.DmN.pht.base.ast.NodeNodesList
import ru.DmN.pht.std.processor.ctx.BodyContext

class NodeBody(tkOperation: Token, nodes: MutableList<Node>, var ctx: BodyContext? = null) : NodeNodesList(tkOperation, nodes) {
    override fun copy(): NodeBody =
        NodeBody(token, copyNodes(), ctx?.copy())
}