package ru.DmN.pht.base.parser.ast

import ru.DmN.pht.base.lexer.Token

class NodeProcessedUse(tkOperation: Token, names: List<String>, nodes: MutableList<Node>, val processed: MutableList<Node>) : NodeUse(tkOperation, names, nodes)