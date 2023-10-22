package ru.DmN.pht.std.imports.ast

import ru.DmN.pht.base.lexer.Token
import ru.DmN.pht.base.ast.Node

class NodeAliasType(tkOperation: Token, val type: String, val new: String) : Node(tkOperation)