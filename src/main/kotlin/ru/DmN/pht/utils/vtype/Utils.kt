package ru.DmN.pht.utils.vtype

import ru.DmN.siberia.utils.vtype.VirtualType

/**
 * Имя без пакета.
 */
val VirtualType.simpleName: String
    get() = name.substring(name.lastIndexOf('.') + 1)

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

inline val VirtualType.nullableType: VVTNullable
    get() = if (this is VVTNullable) VVTNullable(type, true) else VVTNullable(PhtVirtualType.of(this), true)

inline val VirtualType.notNullType: VVTNullable
    get() = if (this is VVTNullable) VVTNullable(type, false) else VVTNullable(PhtVirtualType.of(this), false)

inline val VirtualType.clearNullType: VirtualType
    get() = if (this is VVTNullable) type else this