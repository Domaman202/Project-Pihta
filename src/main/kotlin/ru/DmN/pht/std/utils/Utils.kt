package ru.DmN.pht.std.utils

import org.objectweb.asm.Label
import org.objectweb.asm.Opcodes
import org.objectweb.asm.tree.MethodNode
import ru.DmN.pht.base.Compiler
import ru.DmN.pht.base.compiler.java.ctx.GlobalContext
import ru.DmN.pht.base.utils.*
import ru.DmN.pht.base.utils.Variable
import kotlin.jvm.optionals.getOrNull

val String.desc
    get() = when (this) {
        "void" -> "V"
        "boolean" -> "Z"
        "byte" -> "B"
        "short" -> "S"
        "char" -> "C"
        "int" -> "I"
        "long" -> "J"
        "double" -> "D"
        else -> if (this.startsWith('[')) this.replace('.', '/') else "L${this.replace('.', '/')};"
    }

fun VirtualType.ofPrimitive(): String? = when (name) {
    "void" -> ("java.lang.Void")
    "boolean" -> ("java.lang.Boolean")
    "byte" -> ("java.lang.Byte")
    "char" -> ("java.lang.Character")
    "short" -> ("java.lang.Short")
    "int" -> ("java.lang.Integer")
    "long" -> ("java.lang.Long")
    "float" -> ("java.lang.Float")
    "double" -> ("java.lang.Double")
    else -> null
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

fun findFunction(input: List<VirtualType>, variants: List<VirtualMethod>, ctx: GlobalContext, compiler: Compiler): VirtualMethod {
    return variants[findFunction(input, variants.map { Pair(it.argsc.map { ctx.getType(compiler, it.type) }, it.varargs) })!!.first]
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

fun insertRet(variable: Variable?, rettype: VirtualType, node: MethodNode) {
    variable?.let { loadCast(it, rettype, node) }
    node.visitInsn(
        when (rettype.name) {
            "void" -> Opcodes.RETURN
            "boolean","byte","short","char","int" -> Opcodes.IRETURN
            "long" -> Opcodes.LRETURN
            "float" -> Opcodes.FRETURN
            "double" -> Opcodes.DRETURN
            else -> Opcodes.ARETURN
        }
    )
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
        else throw ClassCastException("$ntype no casts to $otype")
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

fun loadCast(variable: Variable, to: VirtualType, node: MethodNode) {
    val from = variable.type ?: "java.lang.Object"
    if (from.isPrimitive() != to.isPrimitive) {
        if (to.isPrimitive) {
            objectToPrimitive(variable, node)
        } else {
            primitiveToObject(variable, node)
        }
    } else load(variable, node)
}

fun load(variable: Variable, node: MethodNode) {
    if (!variable.tmp) {
        node.visitVarInsn(
            when (variable.type) {
                "void" -> throw RuntimeException()
                "boolean", "byte", "short", "char", "int" -> Opcodes.ILOAD
                "long" -> Opcodes.LLOAD
                "float" -> Opcodes.FLOAD
                "double" -> Opcodes.DLOAD
                else -> Opcodes.ALOAD
            },
            variable.id
        )
    }
}

fun storeCast(variable: Variable, from: VirtualType, node: MethodNode) {
    val to = variable.type ?: "java.lang.Object"
    val tmp = Variable("tmp$${variable.hashCode() + from.hashCode()}", from.name, -1, true)
    if (from.isPrimitive != to.isPrimitive())
        if (to.isPrimitive())
            objectToPrimitive(tmp, node)
        else
            primitiveToObject(tmp, node)
    store(variable, node)
}

fun store(variable: Variable, node: MethodNode) {
    if (!variable.tmp) {
        node.visitVarInsn(
            when (variable.type) {
                "boolean", "byte", "short", "char", "int" -> Opcodes.ISTORE
                "long" -> Opcodes.LSTORE
                "float" -> Opcodes.FSTORE
                "double" -> Opcodes.DSTORE
                else -> Opcodes.ASTORE
            },
            variable.id
        )
    }
}

fun primitiveToObject(variable: Variable, node: MethodNode): String? {
    val start = Label()
    node.visitLabel(start)
    load(variable, node)
    val vtype = when (variable.type) {
        "boolean" -> {
            node.visitMethodInsn(Opcodes.INVOKESTATIC, "java/lang/Boolean", "valueOf", "(Z)Ljava/lang/Boolean;", false)
            "java.lang.Boolean"
        }
        "byte", "short", "char", "long", "float", "double" -> TODO()
        "int" -> {
            node.visitMethodInsn(Opcodes.INVOKESTATIC, "java/lang/Integer", "valueOf", "(I)Ljava/lang/Integer;", false)
            "java.lang.Integer"
        }
        else -> variable.type
    }
    return vtype
}

fun objectToPrimitive(variable: Variable, node: MethodNode): String? {
    val start = Label()
    node.visitLabel(start)
    return when (variable.type) {
        "java.lang.Boolean", "java.lang.Byte", "java.lang.Short", "java.lang.Character", "java.lang.Long", "java.lang.Float", "java.lang.Double" -> TODO()
        "java.lang.Integer" -> {
            load(variable, node)
            node.visitMethodInsn(Opcodes.INVOKESTATIC, "java/lang/Integer", "toInt", "()I", false)
            "int"
        }
        else -> variable.type
    }
}