package ru.DmN.pht.std.ast

import ru.DmN.siberia.utils.VirtualType

/**
 * Адаптируемая под тип нода.
 *
 * P.S. Используется в лямбда нодах.
 */
interface IAdaptableNode {
    /**
     * Проверяет можно ли адаптировать ноду под тип?
     */
    fun isAdaptableTo(type: VirtualType): Boolean

    /**
     * Адаптирует ноду под тип.
     */
    fun adaptTo(type: VirtualType)
}