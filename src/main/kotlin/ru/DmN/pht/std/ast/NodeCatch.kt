package ru.DmN.pht.std.ast

import ru.DmN.siberia.ast.Node
import ru.DmN.siberia.ast.NodeNodesList
import ru.DmN.siberia.lexer.Token
import ru.DmN.siberia.utils.VirtualType

class NodeCatch(token: Token, nodes: MutableList<Node>, val catchers: List<Triple<String, VirtualType, Node?>>) : NodeNodesList(token, nodes)