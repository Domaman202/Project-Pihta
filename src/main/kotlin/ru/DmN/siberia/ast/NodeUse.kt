package ru.DmN.siberia.ast

import ru.DmN.pht.base.lexer.Token
import ru.DmN.pht.base.utils.indent
import ru.DmN.pht.std.utils.text

open class NodeUse(tkOperation: Token, val names: List<String>, nodes: MutableList<Node>) : NodeNodesList(tkOperation, nodes) {
    override fun copy(): NodeUse =
        NodeUse(token, names, copyNodes())

    override fun print(builder: StringBuilder, indent: Int): StringBuilder {
        builder.indent(indent).append('[').append(text)
        names.forEach { builder.append(' ').append(it) }
        return printNodes(builder, indent).append(']')
    }
}