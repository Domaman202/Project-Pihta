package ru.DmN.pht.ast

/**
 * Нода значения.
 */
interface IValueNode {
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