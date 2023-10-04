package ru.DmN.pht.base.parser.ast

import ru.DmN.pht.base.lexer.Token
import ru.DmN.pht.base.utils.indent

open class NodeNodesList(tkOperation: Token, override val nodes: MutableList<Node> = mutableListOf()) : Node(tkOperation) {
    override fun copy(): NodeNodesList =
        NodeNodesList(tkOperation, copyNodes())

    fun copyNodes(): MutableList<Node> =
        nodes.map { it.copy() }.toMutableList()

    override fun print(builder: StringBuilder, indent: Int): StringBuilder =
        printNodes(builder.indent(indent).append('[').append(tkOperation.text), indent).append(']')

    fun printNodes(builder: StringBuilder, indent: Int): StringBuilder {
        if (nodes.isNotEmpty())
            builder.append('\n')
        nodes.forEach { it.print(builder, indent + 1).append('\n') }
        if (nodes.isNotEmpty())
            builder.indent(indent)
        return builder
    }
}