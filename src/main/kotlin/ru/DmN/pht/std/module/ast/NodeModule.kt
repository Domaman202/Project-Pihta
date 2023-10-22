package ru.DmN.pht.std.module.ast

import ru.DmN.pht.base.lexer.Token
import ru.DmN.pht.base.ast.NodeNodesList
import ru.DmN.pht.base.utils.Module

class NodeModule(tkOperation: Token, val data: Map<String, Any?>) : NodeNodesList(tkOperation) {
    lateinit var module: Module
}