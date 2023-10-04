package ru.DmN.pht.std.base.utils

import ru.DmN.pht.base.Processor
import ru.DmN.pht.base.parser.ast.Node
import ru.DmN.pht.base.processor.utils.ProcessingContext
import ru.DmN.pht.base.processor.utils.ValType
import ru.DmN.pht.base.utils.VirtualType
import ru.DmN.pht.base.utils.klassOf
import ru.DmN.pht.std.base.processor.processors.IStdNodeProcessor
import ru.DmN.pht.std.base.processor.utils.ICastable
import java.util.*
import kotlin.jvm.optionals.getOrNull

fun lenArgs(from: List<VirtualType>, to: List<ICastable>) =
    from.mapIndexed { i, it -> to[i].castableTo(it) }.sum()

fun Processor.processNodes(node: Node, ctx: ProcessingContext, mode: ValType): List<Node> =
    node.nodes.map {process(it, ctx, mode)!! }

fun Processor.compute(node: Node, ctx: ProcessingContext): Node =
    this.get(ctx, node).let {
        if (it is IStdNodeProcessor<Node>)
            it.compute(node, this, ctx)
        else node
    }

fun Processor.computeList(node: Node, ctx: ProcessingContext): List<Node> =
    this.get(ctx, node).let {
        if (it is IStdNodeProcessor<Node>)
            it.computeList(node, this, ctx)
//        else node.nodes
        else throw RuntimeException()
    }

fun Processor.computeString(node: Node, ctx: ProcessingContext): String =
    this.get(ctx, node).let {
        if (it is IStdNodeProcessor<Node>)
            it.computeString(node, this, ctx)
//        else node.getValueAsString()
        else throw RuntimeException()
    }

fun Processor.computeInt(node: Node, ctx: ProcessingContext): Int =
    this.get(ctx, node).let {
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

fun findFunction(input: List<VirtualType>, variants: List<Pair<List<VirtualType>, Boolean>>): Pair<Int, Int>? {
//    println(getExecutables(input, variants))
    return getExecutables(input, variants).stream().filter { it.second != -1 }.sorted { a, b -> a.second.compareTo(b.second) }.findFirst().getOrNull()
}

fun getExecutables(input: List<VirtualType>, variants: List<Pair<List<VirtualType>, Boolean>>): List<Pair<Int, Int>> {
    val result = ArrayList<Pair<Int, Int>>()
    variants.forEachIndexed { i, it -> result.add(Pair(i, calcMethod(input, it.first, it.second))) }
    return result
}

fun calcMethod(input: List<VirtualType>, args: List<VirtualType>, varg: Boolean): Int {
    return if (input.isEmpty() && args.isEmpty())
        0
    else if (input.size == args.size || (varg && (input.size >= args.size))) {
        var sum = 0
        val end = args.size - 1
        for (j in 0 until end)
            sum += lenArgs(input[j], args[j]).let { if (it == -1) return -1 else it }
        return sum + (if (varg)
            (lenArgs(input[end], args[end].componentType!!) + input.size - end) * 100
        else lenArgs(input[end], args[end])).let { if (it == -1) return -1 else it }
    } else if (varg && input.isEmpty())
        lenArgs(VirtualType.ofKlass(Any::class.java), args.first().componentType!!) * 100 else -1
}

fun lenArgs(src: VirtualType?, dist: VirtualType): Int {
    return if (src == null) 0
    else if (dist.isPrimitive == src.isPrimitive)
        if (dist.isPrimitive)
            if (src == dist) 0
            else -1
        else if (src.isAssignableFrom(dist))
            if (src == dist) 0
            else lenArgs(src.superclass, dist) + 1
        else -1
    else
        if (dist.isPrimitive)
            src.toPrimitive()?.let { lenArgs(VirtualType.ofKlass(it), dist).let { i -> if (i == -1) return -1 else i } + 1 }
                ?: dist.toPrimitive()?.let { lenArgs(src, VirtualType.ofKlass(klassOf(it))) }
                ?: -1
        else
            dist.toPrimitive()?.let { lenArgs(src, VirtualType.ofKlass(it)).let { i -> if (i == -1) return -1 else i } + 1 }
                ?: src.ofPrimitive()?.let { lenArgs(VirtualType.ofKlass(it), dist) }
                ?: -1
}

fun calcType(otype: VirtualType?, ntype: VirtualType?): Pair<VirtualType?, VirtualType?> {
    return if (otype == ntype)
        Pair(otype, ntype)
    else if (ntype == null)
        Pair(otype, null)
    else if (otype == null)
        Pair(ntype, null)
    else {
        val typeA = otype.ofPrimitive()?.let { VirtualType.ofKlass(it) } ?: otype
        val typeB = ntype.ofPrimitive()?.let { VirtualType.ofKlass(it) } ?: ntype
        if (typeA.isAssignableFrom(typeB))
            Pair(ntype, otype)
        else if (typeB.isAssignableFrom(typeA))
            Pair(otype, ntype)
        else Pair(findCommonSuperclasses(otype, ntype).last { !it.name.startsWith("kotlin.jvm.internal.") }, otype) // todo: remake
//        else throw ClassCastException("$ntype no casts to $otype")
    }
}

fun calcNumberType(a: VirtualType?, b: VirtualType?): VirtualType? = if (calcWeight(a) > calcWeight(b)) a else b

fun calcWeight(type: VirtualType?): Int {
    return when (type?.name) {
        null -> 0
        "byte", "java.lang.Byte" -> 1
        "short", "java.lang.Short" -> 2
        "int", "java.lang.Integer" -> 3
        "long", "java.lang.Long" -> 4
        "float", "java.lang.Float" -> 5
        "double", "java.lang.Double" -> 6
        else -> throw RuntimeException()
    }
}