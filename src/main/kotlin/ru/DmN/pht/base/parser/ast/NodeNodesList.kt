package ru.DmN.pht.base.parser.ast

import ru.DmN.pht.base.lexer.Token

open class NodeNodesList(tkOperation: Token, override val nodes: MutableList<Node> = mutableListOf()) : Node(tkOperation)