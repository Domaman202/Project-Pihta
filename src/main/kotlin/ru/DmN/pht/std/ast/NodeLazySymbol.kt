package ru.DmN.pht.std.ast

import ru.DmN.pht.base.lexer.Token
import ru.DmN.pht.base.ast.Node
import ru.DmN.pht.base.ast.NodeNodesList

class NodeLazySymbol(tkOperation: Token, nodes: MutableList<Node>, var symbol: String?) : NodeNodesList(tkOperation, nodes) {
    override fun copy(): NodeLazySymbol =
        NodeLazySymbol(token, copyNodes(), symbol)
}