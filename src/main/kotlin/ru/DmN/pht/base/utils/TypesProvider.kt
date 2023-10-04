package ru.DmN.pht.base.utils

import java.util.*

abstract class TypesProvider {
    val types: MutableList<VirtualType> = ArrayList()

    open fun typeOfOrNull(name: String) =
        try { typeOf(name) } catch (_: ClassNotFoundException) { null }

    open fun typeOf(name: String): VirtualType =
        types.find { it.name == name } ?: throw RuntimeException()
}