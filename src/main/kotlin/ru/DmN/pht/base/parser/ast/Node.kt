package ru.DmN.pht.base.parser.ast

import ru.DmN.pht.base.lexer.Token
import ru.DmN.pht.base.utils.indent

open class Node(val tkOperation: Token) : IValueNode, Cloneable {
    open val nodes: List<Node>
        get() = emptyList()

    open fun copy(): Node =
        this

    open fun print(builder: StringBuilder, indent: Int): StringBuilder =
        builder.indent(indent).append('[').append(tkOperation.text).append(']')

    fun print(): String = print(StringBuilder(), 0).toString()
}