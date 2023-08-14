package ru.DmN.pht.std.utils

class Variable(val name: String, private var value: Any?) {
    fun set(value: Any?) {
        this.value = value
    }

    fun get(): Any? = this.value
}