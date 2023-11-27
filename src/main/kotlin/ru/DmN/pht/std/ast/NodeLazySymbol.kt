package ru.DmN.pht.std.ast

import ru.DmN.siberia.lexer.Token
import ru.DmN.siberia.ast.Node
import ru.DmN.siberia.ast.NodeNodesList

class NodeLazySymbol(tkOperation: Token, nodes: MutableList<Node>, var symbol: String?) : NodeNodesList(tkOperation, nodes) {
    override fun copy(): NodeLazySymbol =
        NodeLazySymbol(token, copyNodes(), symbol)
}