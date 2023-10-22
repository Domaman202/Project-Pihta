package ru.DmN.pht.base.ast

import ru.DmN.pht.base.lexer.Token

class NodeParsedUse(tkOperation: Token, names: List<String>, nodes: MutableList<Node>, val exports: MutableList<NodeNodesList>) : NodeUse(tkOperation, names, nodes)