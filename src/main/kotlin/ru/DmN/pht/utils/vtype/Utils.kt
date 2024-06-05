package ru.DmN.pht.utils.vtype

import ru.DmN.pht.jvm.utils.vtype.genericsDefine
import ru.DmN.siberia.utils.vtype.VirtualType

inline val String.genericName
    get() = indexOf('$').let { if (it > -1) substring(0, it) else this }

val VirtualType.nameWithGenerics: String
    get() {
        if (genericsDefine.isEmpty())
            return "^$name"
        StringBuilder().run {
            genericsDefine.values.forEachIndexed { i, it ->
                append('^').append(it.name)
                if (i != genericsDefine.size - 1) {
                    append(", ")
                }
            }
            return "^$name<$this>"
        }
    }

val VirtualType.nameWithGens: String
    get() =
        if (this is VVTWithGenerics)
            nameWithGens
        else "^$name"

val VVTWithGenerics.nameWithGens: String
    get() {
        if (genericsDefine.isEmpty())
            return "^$name"
        StringBuilder().run {
            genericsData.values.forEachIndexed { i, it ->
                if (it.isFirst)
                    append(it.first().nameWithGens)
                else append(it.second())
                    .append('^')
                if (i != genericsData.size - 1) {
                    append(", ")
                }
            }
            return "^$name<$this>"
        }
    }

/**
 * Имя без пакета.
 */
inline val VirtualType.simpleName: String
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