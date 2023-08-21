package ru.DmN.pht.base.utils

class Generics(val list: MutableList<Generic> = ArrayList()) {
    operator fun get(name: String): String =
        list.find { it.name == name }!!.type

    companion object {
        val EMPTY = Generics()
    }
}