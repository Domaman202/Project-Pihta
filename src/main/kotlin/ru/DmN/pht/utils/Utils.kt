@file:Suppress("NOTHING_TO_INLINE", "UNCHECKED_CAST")
package ru.DmN.pht.utils

import ru.DmN.pht.ast.IValueNode
import ru.DmN.pht.jvm.utils.vtype.genericsDefine
import ru.DmN.pht.jvm.utils.vtype.superclass
import ru.DmN.pht.processor.utils.ICastable
import ru.DmN.pht.utils.vtype.VVTWithGenerics
import ru.DmN.siberia.ast.Node
import ru.DmN.siberia.utils.Variable
import ru.DmN.siberia.utils.klassOf
import ru.DmN.siberia.utils.vtype.VirtualMethod
import ru.DmN.siberia.utils.vtype.VirtualType
import java.util.stream.Stream

inline val Variable.uniqueName
    get() = "$name$$id"

fun <T> Iterable<T>.asSequenceWith(first: T): Sequence<T> =
    FirstElementSequence(this, first)

inline fun <T> List<T>.dropForEach(count: Int, block: (T) -> Unit) {
    for (i in count until size) {
        block(this[i])
    }
}

inline fun <T> List<T>.dropLastMutable(count: Int): MutableList<T> {
    val new = ArrayList<T>(this.size - count)
    for (i in 0 until this.size - count)
        new += this[i]
    return new
}

inline fun <T> List<T>.dropMutable(count: Int): MutableList<T> {
    val new = ArrayList<T>(this.size - count)
    for (i in count until this.size)
        new += this[i]
    return new
}

/**
 * Нормализация имён:
 *
 * "set-value" -> "setValue"
 * "set-" -> "set_"
 */
fun String.normalizeName(): String =
    if (this.contains('-')) {
        val sb = StringBuilder()
        var i = 0
        while (i < length) {
            sb.append(
                when (val c = this[i]) {
                    '-' -> if (i == lastIndex) '_' else this[++i].uppercase()
                    else -> c
                }
            )
            i++
        }
        sb.toString()
    } else this

inline fun <K, V> Stream<Pair<K, V>>.toMap(): MutableMap<K, V> =
    HashMap<K, V>().apply { this@toMap.forEach { (k, v) -> set(k, v) } }

inline fun <T> Iterable<T>.forEach(first: T, block: (T) -> Unit) {
    block(first)
    forEach(block)
}

fun <T> sequenceOf(nullable: Collection<T>?, iterable: Iterable<T>): Sequence<T> =
    if (nullable == null)
        iterable.asSequence()
    else nullable.asSequence() + iterable.asSequence()

val VirtualType.nameWithGenerics: String
    get() {
        if (genericsDefine.isEmpty())
            return "^$name"
        val sb = StringBuilder()
        genericsDefine.values.forEachIndexed { i, it ->
            sb.append('^').append(it.name)
            if (i != genericsDefine.size - 1) {
                sb.append(", ")
            }
        }
        return "^$name<$sb>"
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
        val sb = StringBuilder()
        genericsData.values.forEachIndexed { i, it ->
            if (it.isFirst)
                sb.append(it.first().nameWithGens)
            else sb.append(it.second()).append('^')
            if (i != genericsData.size - 1) {
                sb.append(", ")
            }
        }
        return "^$name<$sb>"
    }

inline fun <T, R> List<T>.mapIndexedMutable(transform: (Int, T) -> R): MutableList<R> {
    val list = ArrayList<R>(size)
    var i = 0
    for (it in this)
        list.add(transform(i++, it))
    return list
}

val Node.text
    get() = type.operation
val Node.type
    get() = info.type
val Node.isLiteral
    get() = if (this is IValueNode) isLiteral() else false
val Node.isConstClass
    get() = if (this is IValueNode) isConstClass() else false
val Node.valueAsString
    get() = (this as IValueNode).getValueAsString()

fun findLambdaMethod(type: VirtualType): VirtualMethod =
    findLambdaMethodOrNull(type)!!

fun findLambdaMethodOrNull(type: VirtualType): VirtualMethod? =
    type.methods.firstOrNull { it.declaringClass == type && it.modifiers.abstract }

fun lenArgs(from: List<VirtualType>, to: List<ICastable>, varargs: Boolean): Pair<Int, Boolean> =
    if (varargs) {
        val i = lenArgs(to) { val i = it.coerceAtMost(from.size - 1); from[i].run { if (i == from.size - 1) componentType!! else this } } * 100
        val j = lenArgs(to) { from[it] }
        if (i < j)
            Pair(j, false)
        else Pair(i, true)
    } else if (from.size != to.size)
        Pair(-1, false)
    else Pair(lenArgs(to) { from[it] }, false)

fun lenArgs(to: List<ICastable>, getter: (index: Int) -> VirtualType): Int {
    var j = 1
    for (i in to.indices) {
        val k = to[i].castableTo(getter(i))
        if (k == -1)
            return -1
        else j += k
    }
    return j
}

fun VirtualType.ofPrimitive(): String = when (name) {
    "void" -> ("java.lang.Void")
    "boolean" -> ("java.lang.Boolean")
    "byte" -> ("java.lang.Byte")
    "char" -> ("java.lang.Character")
    "short" -> ("java.lang.Short")
    "int" -> ("java.lang.Integer")
    "long" -> ("java.lang.Long")
    "float" -> ("java.lang.Float")
    "double" -> ("java.lang.Double")
    else -> name
}

fun VirtualType.toPrimitive(): String? = when (name) {
    "java.lang.Void" -> ("void")
    "java.lang.Boolean" -> ("boolean")
    "java.lang.Byte" -> ("byte")
    "java.lang.Character" -> ("char")
    "java.lang.Short" -> ("short")
    "java.lang.Integer" -> ("int")
    "java.lang.Long" -> ("long")
    "java.lang.Float" -> ("float")
    "java.lang.Double" -> ("double")
    else -> null
}

fun lenArgs(src: VirtualType?, dist: VirtualType): Int =
    if (src == null) 0
    else if (dist.isPrimitive == src.isPrimitive)
        if (dist.isPrimitive)
            if (src == dist) 0
            else 1
        else if (src.isAssignableFrom(dist))
            if (src == dist) 0
            else lenArgs(src.superclass, dist) + 1
        else -1
    else
        if (dist.isPrimitive)
            src.toPrimitive()
                ?.let { lenArgs(VirtualType.ofKlass(it), dist).let { i -> if (i == -1) return -1 else i } + 1 }
                ?: dist.toPrimitive()?.let { lenArgs(src, VirtualType.ofKlass(klassOf(it))) }
                ?: -1
        else
            dist.toPrimitive()
                ?.let { lenArgs(src, VirtualType.ofKlass(it)).let { i -> if (i == -1) return -1 else i } + 1 }
                ?: lenArgs(VirtualType.ofKlass(src.ofPrimitive()), dist)

fun lenArgsB(src: VirtualType, dist: VirtualType): Int = // todo: я хз что я сделал, но думаю оно недоработано
    if (dist.isPrimitive == src.isPrimitive)
        if (dist.isPrimitive)
            if (src == dist) 0
            else 1
        else if (src.isAssignableFrom(dist) || dist.isAssignableFrom(src))
            if (src == dist) 0
            else lenArgs(src.superclass, dist) + 1
        else -1
    else -1