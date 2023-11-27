package ru.DmN.pht.std.ast

interface IValueNode {
    fun isLiteral(): Boolean = false
    fun isConstClass(): Boolean = false
    fun getValueAsString(): String = throw UnsupportedOperationException()
}