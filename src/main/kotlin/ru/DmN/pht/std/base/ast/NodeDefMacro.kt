package ru.DmN.pht.std.base.ast

import ru.DmN.pht.base.lexer.Token
import ru.DmN.pht.base.parser.ast.Node
import ru.DmN.pht.base.parser.ast.NodeNodesList

class NodeDefMacro(tkOperation: Token, val name: String, val args: List<String>, nodes: MutableList<Node>) : NodeNodesList(tkOperation, nodes)