package ru.DmN.pht.std.macro.ast

import ru.DmN.pht.base.lexer.Token
import ru.DmN.pht.base.parser.ast.Node
import ru.DmN.pht.base.parser.ast.NodeNodesList

class NodeMacro(tkOperation: Token, val name: String, nodes: MutableList<Node>) : NodeNodesList(tkOperation, nodes)