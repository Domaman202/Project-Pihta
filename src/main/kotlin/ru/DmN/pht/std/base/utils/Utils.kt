package ru.DmN.pht.std.base.utils

import org.objectweb.asm.Label
import org.objectweb.asm.Opcodes
import org.objectweb.asm.tree.MethodNode
import ru.DmN.pht.base.compiler.java.Compiler
import ru.DmN.pht.base.utils.*
import ru.DmN.pht.base.utils.Variable
import ru.DmN.pht.std.base.compiler.java.ctx.GlobalContext
import java.util.*
import kotlin.jvm.optionals.getOrNull

fun Generics.getSignature(compiler: Compiler, ctx: GlobalContext): String =
    StringBuilder().apply { list.forEach { append(it.getSignature(compiler, ctx)) } }.toString()

fun Generic.getSignature(compiler: Compiler, ctx: GlobalContext): String =
    "${name}:${ctx.getType(compiler, type).desc}"

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

fun insertRet(variable: Variable?, rettype: VirtualType, node: MethodNode) {
    if (rettype.name == "void")
        node.visitInsn(Opcodes.RETURN)
    else {
        if (variable == null) {
            if (rettype.isPrimitive) {
                node.visitLdcInsn(
                    when (rettype.name) {
                        "boolean", "byte", "short", "char", "int" -> Opcodes.ICONST_0
                        "long" -> Opcodes.LCONST_0
                        "float" -> Opcodes.FCONST_0
                        "double" -> Opcodes.DCONST_0
                        else -> throw Error("Unreachable code")
                    }
                )
            } else node.visitFieldInsn(Opcodes.GETSTATIC, "kotlin/Unit", "INSTANCE", "Lkotlin/Unit;")
        } else loadCast(variable, rettype, node)
        node.visitInsn(
            when (rettype.name) {
                "boolean", "byte", "short", "char", "int" -> Opcodes.IRETURN
                "long" -> Opcodes.LRETURN
                "float" -> Opcodes.FRETURN
                "double" -> Opcodes.DRETURN
                else -> Opcodes.ARETURN
            }
        )
    }
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
    return variants[findFunction(input, variants.map { it -> Pair(it.argsc.map { ctx.getType(compiler, it.type) }, it.varargs) })!!.first]
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

fun loadCast(variable: Variable, to: VirtualType, node: MethodNode) {
    val from = variable.type()
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
    val to = variable.type()
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