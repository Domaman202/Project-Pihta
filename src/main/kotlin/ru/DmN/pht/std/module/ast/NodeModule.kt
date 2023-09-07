package ru.DmN.pht.std.module.ast

import ru.DmN.pht.base.lexer.Token
import ru.DmN.pht.base.parser.ast.NodeNodesList

class NodeModule(tkOperation: Token, val data: Map<String, Any>) : NodeNodesList(tkOperation)