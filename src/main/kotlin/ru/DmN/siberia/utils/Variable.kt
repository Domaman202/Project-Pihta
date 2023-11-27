package ru.DmN.siberia.utils

import kotlin.math.absoluteValue

data class Variable(var name: String, var type: VirtualType?, var id: Int, var tmp: Boolean) {
    fun type(): VirtualType =
        type ?: VirtualType.ofKlass("java.lang.Object")

    companion object {
        fun tmp(hash: Any, type: VirtualType?) =
            Variable(tmp(hash), type, -1, true)

        fun tmp(hash: Any, i: Int = 1) =
            "pht\$tmp$${(hash.hashCode() * i).and(0xFFFF).absoluteValue}"
    }
}