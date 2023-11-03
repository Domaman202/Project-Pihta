package ru.DmN.pht.std.ast

import ru.DmN.pht.base.ast.Node
import ru.DmN.pht.base.ast.NodeNodesList
import ru.DmN.pht.base.lexer.Token

class NodeCompare(token: Token, nodes: MutableList<Node>) : NodeNodesList(token, nodes) {
    override fun copy(): NodeCompare =
        NodeCompare(token, copyNodes())
}