package ru.DmN.pht.base.ast

import ru.DmN.pht.base.lexer.Token
import ru.DmN.pht.base.utils.indent
import ru.DmN.pht.std.utils.text

open class NodeNodesList(token: Token, override val nodes: MutableList<Node> = mutableListOf()) : Node(token), INodesList {
    override fun copy(): NodeNodesList =
        NodeNodesList(token, copyNodes())

    fun copyNodes(): MutableList<Node> =
        nodes.map { it.copy() }.toMutableList()

    override fun print(builder: StringBuilder, indent: Int): StringBuilder =
        printNodes(builder.indent(indent).append('[').append(text), indent).append(']')

    fun printNodes(builder: StringBuilder, indent: Int): StringBuilder {
        if (nodes.isNotEmpty())
            builder.append('\n')
        nodes.forEach { it.print(builder, indent + 1).append('\n') }
        if (nodes.isNotEmpty())
            builder.indent(indent)
        return builder
    }
}