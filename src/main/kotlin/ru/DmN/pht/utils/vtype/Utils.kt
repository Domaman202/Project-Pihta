package ru.DmN.pht.utils.vtype

import ru.DmN.siberia.utils.vtype.VirtualType

/**
 * Имя без пакета.
 */
val VirtualType.simpleName: String
    get() = this.name.substring(this.name.lastIndexOf('.') + 1)

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