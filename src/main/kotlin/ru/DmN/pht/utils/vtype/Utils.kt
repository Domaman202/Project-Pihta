package ru.DmN.pht.utils.vtype

import ru.DmN.siberia.utils.vtype.VirtualType

/**
 * Тип является массивом?
 */
inline val VirtualType.isArray: Boolean
    get() = this is VVTArray

/**
 * Тип массива из элементов данного типа.
 */
inline val VirtualType.arrayType: VVTArray
    get() = VVTArray(PhtVirtualType.of(this))