package ru.DmN.pht.base.parser.ast

import ru.DmN.pht.base.lexer.Token
import ru.DmN.pht.base.utils.indent

class NodeUse(tkOperation: Token, val names: List<String>, nodes: MutableList<Node>) : NodeNodesList(tkOperation, nodes) {
    override fun print(builder: StringBuilder, indent: Int): StringBuilder {
        builder.indent(indent).append('[').append(tkOperation.text).append(']')
        names.forEach { builder.append(' ').append(it) }
        return builder
    }
}