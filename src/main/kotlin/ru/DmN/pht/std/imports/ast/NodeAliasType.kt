package ru.DmN.pht.std.imports.ast

import ru.DmN.siberia.lexer.Token
import ru.DmN.siberia.ast.Node

class NodeAliasType(tkOperation: Token, val type: String, val new: String) : Node(tkOperation)