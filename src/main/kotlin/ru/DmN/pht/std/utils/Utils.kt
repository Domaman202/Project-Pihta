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
        else -> {
            if (this[0] == '[') {
                var i = 0
                while (this[i] == '[') i++
                val clazz = this.substring(i)
                if (this[1] == 'L' || clazz.isPrimitive())
                    this.className
                else "${this.substring(0, i)}L${clazz.className};"
            }
            else "L${this.className};"
        }
    }
val String.className
    get() = this.replace('.', '/')

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
    return when (variable.type) {
        "boolean" -> primitiveToObject(node, 'Z', "java.lang.Boolean")
        "byte" -> primitiveToObject(node, 'B', "java.lang.Byte")
        "short" -> primitiveToObject(node, 'S', "java.lang.Short")
        "char" -> primitiveToObject(node, 'C', "java.lang.Character")
        "int" -> primitiveToObject(node, 'I', "java.lang.Integer")
        "long" -> primitiveToObject(node, 'J', "java.lang.Long")
        "float" -> primitiveToObject(node, 'F', "java.lang.Float")
        "double" -> primitiveToObject(node, 'D', "java.lang.Double")
        else -> variable.type
    }
}

private fun primitiveToObject(node: MethodNode, input: Char, type: String): String {
    node.visitMethodInsn(Opcodes.INVOKESTATIC, type.replace('.', '/'), "valueOf", "($input)${type.desc}", false)
    return type
}

fun objectToPrimitive(variable: Variable, node: MethodNode): String? {
    val start = Label()
    node.visitLabel(start)
    return when (val type = variable.type) {
        "java.lang.Boolean" -> objectToPrimitive(variable, node, type, "boolean", 'Z')
        "java.lang.Byte" -> objectToPrimitive(variable, node, type, "byte", 'B')
        "java.lang.Short" -> objectToPrimitive(variable, node, type, "short", 'S')
        "java.lang.Character" -> objectToPrimitive(variable, node, type, "char", 'C')
        "java.lang.Integer" -> objectToPrimitive(variable, node, type, "int", 'I')
        "java.lang.Long" -> objectToPrimitive(variable, node, type, "long", 'J')
        "java.lang.Float" -> objectToPrimitive(variable, node, type, "float", 'F')
        "java.lang.Double" -> objectToPrimitive(variable, node, type, "double", 'D')
        else -> type
    }
}

private fun objectToPrimitive(variable: Variable, node: MethodNode, owner: String, name: String, rettype: Char): String {
    load(variable, node)
    node.visitMethodInsn(Opcodes.INVOKESTATIC, owner.replace('.', '/'), "${name}Value", "()$rettype", false)
    return name
}