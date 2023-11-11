package ru.DmN.pht.std.ast

import ru.DmN.pht.base.ast.Node
import ru.DmN.pht.base.ast.NodeNodesList
import ru.DmN.pht.base.lexer.Token

class NodeFieldA(token: Token, nodes: MutableList<Node>) : NodeNodesList(token, nodes), IStaticallyNode {
    override var static: Boolean = false
}