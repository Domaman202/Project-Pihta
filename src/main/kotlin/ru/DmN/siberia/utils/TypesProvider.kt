package ru.DmN.siberia.utils

import ru.DmN.siberia.processor.utils.JBTP
import ru.DmN.siberia.processor.utils.JRTP
import java.util.*

abstract class TypesProvider {
    val types: MutableList<VirtualType> = ArrayList()

    open fun typeOf(name: String): VirtualType =
        types.find { it.name == name } ?: throw RuntimeException()

    open fun typeOfOrNull(name: String) =
        try { typeOf(name) } catch (_: ClassNotFoundException) { null }

    companion object {
        val JAVA = JRTP()
    }
}