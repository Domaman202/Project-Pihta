package ru.DmN.pht.base.ast

interface IValueNode {
    fun isLiteral(): Boolean = false
    fun isConst(): Boolean = false
    fun isConstClass(): Boolean = false
    fun getConstValueAsString(): String = if (isConst()) getValueAsString() else throw RuntimeException()
    fun getValueAsString(): String = throw UnsupportedOperationException()
}