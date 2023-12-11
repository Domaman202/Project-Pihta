package ru.DmN.pht.std.ast

import ru.DmN.siberia.ast.Node
import ru.DmN.siberia.ast.NodeNodesList
import ru.DmN.siberia.lexer.Token

class NodeNamedList(token: Token, nodes: MutableList<Node>, val name: String) : NodeNodesList(token, nodes)