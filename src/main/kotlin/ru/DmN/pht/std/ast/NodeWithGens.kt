package ru.DmN.pht.std.ast

import ru.DmN.siberia.ast.Node
import ru.DmN.siberia.ast.NodeNodesList
import ru.DmN.siberia.lexer.Token
import ru.DmN.siberia.utils.VirtualType

class NodeWithGens(token: Token, nodes: MutableList<Node>, val generics: List<VirtualType>) : NodeNodesList(token, nodes)