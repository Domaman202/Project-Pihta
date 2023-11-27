package ru.DmN.siberia.ast

import ru.DmN.siberia.lexer.Token
import ru.DmN.siberia.utils.indent
import ru.DmN.pht.std.utils.text

/**
 * Базовая AST нода
 */
open class Node(val token: Token) {
    /**
     * Копирует ноду.
     * Перегрузите это если в вашей ноде есть что изменять.
     */
    open fun copy(): Node =
        this

    /**
     * Печатает ноду.
     *
     * @param indent отступ
     */
    open fun print(builder: StringBuilder, indent: Int): StringBuilder =
        builder.indent(indent).append('[').append(text).append(']')

    fun print(): String = print(StringBuilder(), 0).toString()
}