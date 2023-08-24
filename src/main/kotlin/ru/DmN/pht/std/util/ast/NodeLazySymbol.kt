package ru.DmN.pht.std.util.ast

import ru.DmN.pht.base.lexer.Token
import ru.DmN.pht.base.parser.ast.Node
import ru.DmN.pht.base.parser.ast.NodeNodesList

class NodeLazySymbol(tkOperation: Token, list: MutableList<Node>, var symbol: String?) : NodeNodesList(tkOperation, list)