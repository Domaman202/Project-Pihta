package ru.DmN.pht.base.ast

import ru.DmN.pht.base.lexer.Token
import ru.DmN.pht.base.utils.indent

open class Node(val token: Token) : IValueNode, Cloneable {
    open val nodes: List<Node>
        get() = emptyList()
    open fun copy(): Node =
        this

    open fun print(builder: StringBuilder, indent: Int): StringBuilder =
        builder.indent(indent).append('[').append(token.text).append(']')

    fun print(): String = print(StringBuilder(), 0).toString()
}