package ru.DmN.pht.base.parser.ast

interface IValueNode {
    fun isConst(): Boolean = false
    fun getConstValueAsString(): String = if (isConst()) getValueAsString() else throw RuntimeException()
    fun getValueAsString(): String = throw UnsupportedOperationException()
}