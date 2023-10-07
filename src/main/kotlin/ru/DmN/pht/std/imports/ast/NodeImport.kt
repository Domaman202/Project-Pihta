package ru.DmN.pht.std.imports.ast

import ru.DmN.pht.base.lexer.Token
import ru.DmN.pht.base.parser.ast.Node

class NodeImport(tkOperation: Token, val module: String, val data: Map<String, List<Any?>>) : Node(tkOperation)