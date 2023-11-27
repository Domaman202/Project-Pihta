package ru.DmN.pht.std.module.ast

import ru.DmN.siberia.lexer.Token
import ru.DmN.siberia.ast.NodeNodesList
import ru.DmN.siberia.utils.Module

class NodeModule(tkOperation: Token, val data: Map<String, Any?>) : NodeNodesList(tkOperation) {
    lateinit var module: Module
}