package ru.DmN.pht.base.utils

data class Variable(var name: String, var type: String?, var id: Int, var tmp: Boolean) {
    fun type(): String =
        type ?: "java.lang.Object"
}