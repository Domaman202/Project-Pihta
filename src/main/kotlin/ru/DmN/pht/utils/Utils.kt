package ru.DmN.pht.std.utils

import ru.DmN.pht.std.ast.IValueNode
import ru.DmN.pht.std.processor.utils.ICastable
import ru.DmN.pht.std.processors.IStdNodeProcessor
import ru.DmN.siberia.Processor
import ru.DmN.siberia.ast.INodesList
import ru.DmN.siberia.ast.Node
import ru.DmN.siberia.processor.ctx.ProcessingContext
import ru.DmN.siberia.processor.utils.ValType
import ru.DmN.siberia.utils.VirtualMethod
import ru.DmN.siberia.utils.VirtualType
import ru.DmN.siberia.utils.klassOf

val VirtualType.nameWithGenerics: String
    get() {
        if (generics.isEmpty())
            return name
        val sb = StringBuilder()
        generics.forEachIndexed { i, it ->
            sb.append(it.second.name)
            if (i != generics.size - 1) {
                sb.append(", ")
            }
        }
        return "$name<$sb>"
    }

inline fun <T, R> List<T>.mapMutable(transform: (T) -> R): MutableList<R> {
    val list = ArrayList<R>(this.size)
    for (it in this)
        list.add(transform(it))
    return list
}

val Node.text
    get() = this.type.operation
val Node.type
    get() = this.info.type
val Node.isLiteral
    get() = if (this is IValueNode) this.isLiteral() else false
val Node.isConstClass
    get() = if (this is IValueNode) this.isConstClass() else false
val Node.valueAsString
    get() = (this as IValueNode).getValueAsString()

fun findLambdaMethod(type: VirtualType): VirtualMethod =
    type.methods.first { it.declaringClass == type && it.modifiers.abstract }

fun lenArgs(from: List<VirtualType>, to: List<ICastable>, varargs: Boolean): Int =
    if (varargs)
        lenArgs(to) {
            val i = it.coerceAtMost(from.size - 1)
            from[i].run { if (i == from.size - 1) componentType!! else this }
        } * 100
    else if (from.size != to.size)
        -1
    else lenArgs(to) { from[it] }

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

fun Processor.processNodes(node: INodesList, ctx: ProcessingContext, mode: ValType): List<Node> =
    node.nodes.map { process(it, ctx, mode)!! }

fun Processor.computeStringNodes(node: INodesList, ctx: ProcessingContext): List<String> =
    node.nodes.map { computeString(it, ctx) }

fun Processor.compute(node: Node, ctx: ProcessingContext): Node =
    this.get(node, ctx).let {
        if (it is IStdNodeProcessor<Node>)
            it.compute(node, this, ctx)
        else node
    }

fun Processor.computeList(node: Node, ctx: ProcessingContext): List<Node> =
    this.computeListOr(node, ctx)!!

fun Processor.computeListOr(node: Node, ctx: ProcessingContext): List<Node>? =
    this.get(node, ctx).let {
        if (it is IStdNodeProcessor<Node>)
            it.computeList(node, this, ctx)
        else throw UnsupportedOperationException()
    }

fun Processor.computeType(node: Node, ctx: ProcessingContext): VirtualType =
    this.computeTypeOr(node, ctx)!!

fun Processor.computeTypeOr(node: Node, ctx: ProcessingContext): VirtualType? =
    this.get(node, ctx).let {
        if (it is IStdNodeProcessor<Node>)
            it.computeType(node, this, ctx)
        else throw UnsupportedOperationException()
    }

fun Processor.computeTypesOr(node: Node, ctx: ProcessingContext): List<VirtualType>? =
    this.get(node, ctx).let {
        if (it is IStdNodeProcessor<Node>)
            it.computeTypes(node, this, ctx)
        else null
    }

fun Processor.computeGenericType(node: Node, ctx: ProcessingContext): String? =
    this.get(node, ctx).let {
        if (it is IStdNodeProcessor<Node>)
            it.computeGenericType(node, this, ctx)
        else throw UnsupportedOperationException()
    }

fun Processor.computeString(node: Node, ctx: ProcessingContext): String =
    this.computeStringOr(node, ctx)!!

fun Processor.computeStringOr(node: Node, ctx: ProcessingContext): String? =
    this.get(node, ctx).let {
        if (it is IStdNodeProcessor<Node>)
            it.computeString(node, this, ctx)
        else throw UnsupportedOperationException()
    }

fun Processor.computeInt(node: Node, ctx: ProcessingContext): Int =
    this.computeIntOr(node, ctx)!!

fun Processor.computeIntOr(node: Node, ctx: ProcessingContext): Int? =
    this.get(node, ctx).let {
        if (it is IStdNodeProcessor<Node>)
            it.computeInt(node, this, ctx)
        else throw UnsupportedOperationException()
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
