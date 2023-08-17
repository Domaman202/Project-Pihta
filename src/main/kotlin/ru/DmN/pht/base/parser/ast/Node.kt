package ru.DmN.pht.base.parser.ast

import ru.DmN.pht.base.lexer.Token
import ru.DmN.pht.base.utils.indent

open class Node(val tkOperation: Token) : IValueNode {
    open val nodes: List<Node>
        get() = emptyList()
    val attributes: MutableMap<String, Any?> = HashMap()

    open fun print(builder: StringBuilder, indent: Int): StringBuilder {
        builder.indent(indent).append('[').append(tkOperation.text)
        if (nodes.isNotEmpty())
            builder.append('\n')
        nodes.forEach { it.print(builder, indent + 1).append('\n') }
        if (nodes.isNotEmpty())
            builder.indent(indent)
        return builder.append(']')
    }

    fun print(): String = print(StringBuilder(), 0).toString()
}