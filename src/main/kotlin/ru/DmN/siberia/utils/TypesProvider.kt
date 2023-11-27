package ru.DmN.siberia.utils

import ru.DmN.siberia.processor.utils.JRTP

/**
 * Провайдер типов.
 */
abstract class TypesProvider {
    /**
     * Список типов.
     */
    val types: MutableList<VirtualType> = ArrayList()

    /**
     * Ищет тип по имени, иначе выкидывает исключение.
     */
    open fun typeOf(name: String): VirtualType =
        types.find { it.name == name } ?: throw RuntimeException("Type '$name not founded!'")

    /**
     * Ищет тип по имени, иначе возвращает null.
     */
    open fun typeOfOrNull(name: String) =
        try { typeOf(name) } catch (_: ClassNotFoundException) { null }

    companion object {
        val JAVA = JRTP()
    }
}