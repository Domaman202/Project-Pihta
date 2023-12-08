package ru.DmN.pht.std.ast

import ru.DmN.siberia.ast.Node
import ru.DmN.siberia.ast.NodeNodesList
import ru.DmN.siberia.lexer.Token

class NodeFieldA(token: Token, nodes: MutableList<Node>, override var static: Boolean = false) : NodeNodesList(token, nodes), IStaticallyNode {
    override fun copy(): NodeNodesList =
        NodeFieldA(token, copyNodes(), static)
}