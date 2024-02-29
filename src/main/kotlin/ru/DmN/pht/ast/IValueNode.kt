package ru.DmN.pht.ast

import ru.DmN.siberia.ast.Node

/**
 * Нода значения.
 */
interface IValueNode : Node {
    /**
     * Нода является литералом?
     */
    fun isLiteral(): Boolean = false

    /**
     * Нода является константой класса?
     */
    fun isConstClass(): Boolean = false

    /**
     * Получения значения ноды в виде строки.
     */
    fun getValueAsString(): String = throw UnsupportedOperationException()
}