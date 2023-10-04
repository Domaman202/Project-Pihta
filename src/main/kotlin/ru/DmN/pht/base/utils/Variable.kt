package ru.DmN.pht.base.utils

data class Variable(var name: String, var type: VirtualType?, var id: Int, var tmp: Boolean) {
    fun type(): VirtualType =
        type ?: VirtualType.OBJECT

    companion object {
        fun tmp(hash: Any, type: VirtualType?) =
            Variable("tmp$${hash.hashCode()}", type, -1, true)
    }
}