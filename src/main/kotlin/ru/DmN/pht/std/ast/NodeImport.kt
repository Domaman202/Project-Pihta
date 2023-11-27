package ru.DmN.pht.std.ast

import ru.DmN.siberia.lexer.Token
import ru.DmN.siberia.ast.Node

class NodeImport(tkOperation: Token, val module: String, val data: Map<String, List<Any?>>) : Node(tkOperation)