package ru.DmN.pht.std.ast

import ru.DmN.siberia.ast.Node
import ru.DmN.siberia.ast.NodeNodesList
import ru.DmN.siberia.lexer.Token

class NodeFieldA(token: Token, nodes: MutableList<Node>) : NodeNodesList(token, nodes), IStaticallyNode {
    override var static: Boolean = false
}