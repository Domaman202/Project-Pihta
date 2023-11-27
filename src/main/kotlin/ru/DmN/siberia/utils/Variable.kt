package ru.DmN.siberia.utils

import kotlin.math.absoluteValue

/**
 * Переменная.
 */
data class Variable(
    /**
     * Имя.
     */
    var name: String,
    /**
     * Тип.
     */
    var type: VirtualType?,
    /**
     * Id переменной.
     *
     * Для tmp равен -1.
     */
    var id: Int,
    /**
     * Существует ли переменная?
     */
    var tmp: Boolean
) {
    /**
     * Возвращает тип, в случае если он не определён возвращает тип объекта.
     */
    fun type(): VirtualType =
        type ?: VirtualType.ofKlass("java.lang.Object")

    companion object {
        /**
         * Создаёт tmp переменную из хеша и типа (опционально)
         */
        fun tmp(hash: Any, type: VirtualType?) =
            Variable(tmp(hash), type, -1, true)

        /**
         * Создаёт tmp переменную из хеша и сдвига хеша (по умолчанию 1)
         */
        fun tmp(hash: Any, i: Int = 1) =
            "pht\$tmp$${(hash.hashCode() * i).and(0xFFFF).absoluteValue}"
    }
}