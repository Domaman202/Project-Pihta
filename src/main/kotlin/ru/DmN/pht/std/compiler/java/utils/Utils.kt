package ru.DmN.pht.std.compiler.java.utils

import org.objectweb.asm.Label
import org.objectweb.asm.MethodVisitor
import org.objectweb.asm.Opcodes
import ru.DmN.siberia.utils.IContextCollection
import ru.DmN.siberia.utils.Variable
import ru.DmN.siberia.utils.VirtualType
import ru.DmN.pht.std.compiler.java.ctx.BodyContext
import ru.DmN.pht.std.compiler.java.ctx.ClassContext
import ru.DmN.pht.std.compiler.java.ctx.MethodContext
import ru.DmN.pht.std.processor.utils.isEnum

fun <T : IContextCollection<T>> T.with(ctx: ClassContext) =
    this.with("pht/class", ctx)
fun <T : IContextCollection<T>> T.with(ctx: MethodContext) =
    this.with("pht/method", ctx)
fun <T : IContextCollection<T>> T.with(ctx: BodyContext) =
    this.with("pht/body", ctx)

fun IContextCollection<*>.isClass() =
    contexts.containsKey("pht/class") || isEnum()
fun IContextCollection<*>.isMethod() =
    contexts.containsKey("pht/method")
fun IContextCollection<*>.isBody() =
    contexts.containsKey("pht/body")

val IContextCollection<*>.clazz
    get() = contexts["pht/class"] as ClassContext
val IContextCollection<*>.method
    get() = contexts["pht/method"] as MethodContext
val IContextCollection<*>.body
    get() = this.bodyOrNull!!
val IContextCollection<*>.bodyOrNull
    get() = contexts["pht/body"] as BodyContext?

fun load(variable: Variable, node: MethodVisitor) {
    if (!variable.tmp) {
        load(variable.type?.name, variable.id, node)
    }
}

fun load(type: String?, id: Int, node: MethodVisitor) {
    node.visitVarInsn(
        when (type) {
            "void" -> throw RuntimeException()
            "boolean", "byte", "short", "char", "int" -> Opcodes.ILOAD
            "long" -> Opcodes.LLOAD
            "float" -> Opcodes.FLOAD
            "double" -> Opcodes.DLOAD
            else -> Opcodes.ALOAD
        },
        id
    )
}

fun storeCast(variable: Variable, from: VirtualType, node: MethodVisitor) {
    val to = variable.type()
    val tmp = Variable("tmp$${variable.hashCode() + from.hashCode()}", from, -1, true)
    if (from.isPrimitive != to.isPrimitive)
        if (to.isPrimitive)
            objectToPrimitive(tmp, node)
        else
            primitiveToObject(tmp, node)
    else if (from.isPrimitive && to.isPrimitive)
        bytecodeCast(tmp.type().name, to.name, node)
    store(variable, node)
}

fun store(variable: Variable, node: MethodVisitor) {
    if (!variable.tmp) {
        node.visitVarInsn(
            when (variable.type().name) {
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

fun bytecodeCast(from: String, to: String, node: MethodVisitor) {
    node.visitInsn(when (from) {
        "boolean", "byte", "short", "char", "int" -> when (to) {
            "boolean",
            "byte"   -> Opcodes.I2B
            "short"  -> Opcodes.I2S
            "char"   -> Opcodes.I2C
            "long"   -> Opcodes.I2L
            "float"  -> Opcodes.I2F
            "double" -> Opcodes.I2D
            else        -> return
        }
        "long" -> when (to) {
            "boolean",
            "byte",
            "short",
            "char",
            "int"    -> Opcodes.L2I
            "float"  -> Opcodes.L2F
            "double" -> Opcodes.L2D
            else     -> return
        }
        "float" -> when (to) {
            "boolean",
            "byte",
            "short",
            "char",
            "int"    -> Opcodes.F2I
            "long"   -> Opcodes.F2L
            "double" -> Opcodes.F2D
            else     -> return
        }
        "double" -> when (to) {
            "boolean",
            "byte",
            "short",
            "char",
            "int"   -> Opcodes.D2I
            "long"  -> Opcodes.D2L
            "float" -> Opcodes.D2F
            else    -> return
        }
        else -> return
    })
}

fun primitiveToObject(variable: Variable, node: MethodVisitor): VirtualType? {
    val start = Label()
    node.visitLabel(start)
    load(variable, node)
    return when (variable.type().name) {
        "boolean" -> primitiveToObject(node, 'Z', VirtualType.ofKlass("java.lang.Boolean"))
        "byte" -> primitiveToObject(node, 'B', VirtualType.ofKlass("java.lang.Byte"))
        "short" -> primitiveToObject(node, 'S', VirtualType.ofKlass("java.lang.Short"))
        "char" -> primitiveToObject(node, 'C', VirtualType.ofKlass("java.lang.Character"))
        "int" -> primitiveToObject(node, 'I', VirtualType.ofKlass("java.lang.Integer"))
        "long" -> primitiveToObject(node, 'J', VirtualType.ofKlass("java.lang.Long"))
        "float" -> primitiveToObject(node, 'F', VirtualType.ofKlass("java.lang.Float"))
        "double" -> primitiveToObject(node, 'D', VirtualType.ofKlass("java.lang.Double"))
        else -> variable.type
    }
}

private fun primitiveToObject(node: MethodVisitor, input: Char, type: VirtualType): VirtualType {
    node.visitMethodInsn(Opcodes.INVOKESTATIC, type.className, "valueOf", "($input)${type.desc}", false)
    return type
}

fun objectToPrimitive(variable: Variable, node: MethodVisitor): VirtualType? {
    val start = Label()
    node.visitLabel(start)
    return when (val type = variable.type().name) {
        "java.lang.Boolean" -> objectToPrimitive(variable, node, type, VirtualType.BOOLEAN, 'Z')
        "java.lang.Byte" -> objectToPrimitive(variable, node, type, VirtualType.BYTE, 'B')
        "java.lang.Short" -> objectToPrimitive(variable, node, type, VirtualType.SHORT, 'S')
        "java.lang.Character" -> objectToPrimitive(variable, node, type, VirtualType.CHAR, 'C')
        "java.lang.Integer" -> objectToPrimitive(variable, node, type, VirtualType.INT, 'I')
        "java.lang.Long" -> objectToPrimitive(variable, node, type, VirtualType.LONG, 'J')
        "java.lang.Float" -> objectToPrimitive(variable, node, type, VirtualType.FLOAT, 'F')
        "java.lang.Double" -> objectToPrimitive(variable, node, type, VirtualType.DOUBLE, 'D')
        else -> variable.type
    }
}

private fun objectToPrimitive(variable: Variable, node: MethodVisitor, owner: String, name: VirtualType, rettype: Char): VirtualType {
    load(variable, node)
    node.visitMethodInsn(Opcodes.INVOKESTATIC, owner.replace('.', '/'), "${name}Value", "()$rettype", false)
    return name
}