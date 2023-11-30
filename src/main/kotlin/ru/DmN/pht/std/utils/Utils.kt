package ru.DmN.pht.std.utils

import ru.DmN.siberia.Processor
import ru.DmN.siberia.ast.INodesList
import ru.DmN.pht.std.ast.IValueNode
import ru.DmN.siberia.ast.Node
import ru.DmN.siberia.processor.ctx.ProcessingContext
import ru.DmN.siberia.processor.utils.ValType
import ru.DmN.siberia.utils.VirtualMethod
import ru.DmN.siberia.utils.VirtualType
import ru.DmN.siberia.utils.klassOf
import ru.DmN.pht.std.processors.IStdNodeProcessor
import ru.DmN.pht.std.processor.utils.ICastable
import java.util.*

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
    var j = 0
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

val Node.isComputeList: Boolean
    get() = if (this is IStdNodeProcessor<*>) this.isComputeList else false

fun Processor.computeList(node: Node, ctx: ProcessingContext): List<Node> =
    this.get(node, ctx).let {
        if (it is IStdNodeProcessor<Node>)
            it.computeList(node, this, ctx)
//        else node.nodes
        else throw RuntimeException()
    }

val Node.isComputeString: Boolean
    get() = if (this is IStdNodeProcessor<*>) this.isComputeString else false

fun Processor.computeString(node: Node, ctx: ProcessingContext): String =
    this.get(node, ctx).let {
        if (it is IStdNodeProcessor<Node>)
            it.computeString(node, this, ctx)
//        else node.getValueAsString()
        else throw RuntimeException()
    }


val Node.isComputeInt: Boolean
    get() = if (this is IStdNodeProcessor<*>) this.isComputeInt else false

fun Processor.computeInt(node: Node, ctx: ProcessingContext): Int =
    this.get(node, ctx).let {
        if (it is IStdNodeProcessor<Node>)
            it.computeInt(node, this, ctx)
//        else node.getValueAsString().toInt()
        else throw RuntimeException()
    }

fun findCommonSuperclasses(vararg classes: VirtualType): List<VirtualType> {
    val commonSuperclasses = mutableListOf<VirtualType>()
    val firstClass = classes.firstOrNull()
    if (firstClass != null) {
        val superClassSet = mutableSetOf<VirtualType>()
        superClassSet.addAll(firstClass.parents)
        for (classToCheck in classes.drop(1))
            superClassSet.retainAll(classToCheck.allSuperclassesAndInterfaces())
        commonSuperclasses.addAll(superClassSet)
    }
    return commonSuperclasses
}

fun VirtualType.allSuperclassesAndInterfaces(): Set<VirtualType> {
    val superclasses = mutableSetOf<VirtualType>()
    val queue = LinkedList<VirtualType>()
    queue.add(this)
    while (queue.isNotEmpty()) {
        val currentClass = queue.poll()
        superclasses.add(currentClass)
        queue.addAll(currentClass.parents)
        val superclass = currentClass.superclass
        if (superclass != null) {
            queue.add(superclass)
        }
    }
    return superclasses
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
