package ru.DmN.pht.std.base.ast

import ru.DmN.pht.base.lexer.Token
import ru.DmN.pht.base.parser.ast.Node

class NodeMacroArg(tkOperation: Token, val name: String) : Node(tkOperation)