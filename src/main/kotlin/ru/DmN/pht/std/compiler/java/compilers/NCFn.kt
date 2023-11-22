package ru.DmN.pht.std.compiler.java.compilers

import org.objectweb.asm.Opcodes
import org.objectweb.asm.tree.ClassNode
import org.objectweb.asm.tree.MethodNode
import ru.DmN.pht.base.compiler.java.Compiler
import ru.DmN.pht.base.compiler.java.compilers.INodeCompiler
import ru.DmN.pht.base.compiler.java.utils.CompilationContext
import ru.DmN.pht.base.processor.utils.javaClassVersion
import ru.DmN.pht.base.utils.Variable
import ru.DmN.pht.base.utils.VirtualField.VirtualFieldImpl
import ru.DmN.pht.base.utils.VirtualType.VirtualTypeImpl
import ru.DmN.pht.std.ast.NodeFn
import ru.DmN.pht.std.compiler.java.compilers.NCDefn.visit
import ru.DmN.pht.std.compiler.java.ctx.ClassContext
import ru.DmN.pht.std.compiler.java.utils.load
import ru.DmN.pht.std.compiler.java.utils.method
import ru.DmN.pht.std.compiler.java.utils.with
import ru.DmN.pht.std.utils.findLambdaMethod
import kotlin.math.absoluteValue

object NCFn : INodeCompiler<NodeFn> {
    override fun compileVal(node: NodeFn, compiler: Compiler, ctx: CompilationContext): Variable {
        if (node.refs.isEmpty()) {
            val name = "PhtLambda${node.hashCode().absoluteValue}"
            val clazz = ClassNode()
            compiler.classes[name] = clazz
            clazz.visit(
                ctx.javaClassVersion,
                Opcodes.ACC_PUBLIC + Opcodes.ACC_FINAL,
                name,
                null,
                node.type!!.superclass?.className ?: "java/lang/Object",
                if (node.type!!.isInterface) arrayOf(node.type!!.className) else emptyArray()
            )
            clazz.visitField(
                Opcodes.ACC_PUBLIC + Opcodes.ACC_FINAL + Opcodes.ACC_STATIC,
                "INSTANCE",
                "L${clazz.name};",
                null,
                null
            )
            clazz.visitMethod(
                Opcodes.ACC_PRIVATE,
                "<init>",
                "()V",
                null,
                null
            ).run {
                visitVarInsn(Opcodes.ALOAD, 0)
                visitMethodInsn(Opcodes.INVOKESPECIAL, clazz.superName, "<init>", "()V", false)
                visitInsn(Opcodes.RETURN)
            }
            clazz.visitMethod(
                Opcodes.ACC_STATIC + Opcodes.ACC_FINAL,
                "<clinit>",
                "()V",
                null,
                null
            ).run {
                visitTypeInsn(Opcodes.NEW, clazz.name)
                visitInsn(Opcodes.DUP)
                visitMethodInsn(Opcodes.INVOKESPECIAL, clazz.name, "<init>", "()V", false)
                visitFieldInsn(Opcodes.PUTSTATIC, clazz.name, "INSTANCE", "L${clazz.name};")
                visitInsn(Opcodes.RETURN)
            }
            val method = findLambdaMethod(node.type!!)
            clazz.visitMethod(
                Opcodes.ACC_PUBLIC + Opcodes.ACC_FINAL,
                method.name,
                method.desc,
                null,
                null
            ).run {
                this as MethodNode
                visit(node, method, compiler, ctx.with(ClassContext(clazz, VirtualTypeImpl(name, mutableListOf(node.type!!)))))
            }
            ctx.method.node.visitFieldInsn(Opcodes.GETSTATIC, clazz.name, "INSTANCE", "L${clazz.name};")
            return Variable.tmp(node, node.type)
        } else {
            val type = VirtualTypeImpl(node.name, mutableListOf(node.type!!))
            compiler.tp.types += type
            val clazz = ClassNode()
            compiler.classes[node.name] = clazz
            clazz.visit(
                ctx.javaClassVersion,
                Opcodes.ACC_PUBLIC + Opcodes.ACC_FINAL,
                node.name,
                null,
                node.type!!.superclass?.className ?: "java/lang/Object",
                if (node.type!!.isInterface) arrayOf(node.type!!.className) else emptyArray()
            )
            val ctor = clazz.visitMethod(
                Opcodes.ACC_PUBLIC,
                "<init>",
                node.refs.run {
                    val str = StringBuilder()
                    forEach { str.append(it.type.desc) }
                    "($str)V"
                },
                null,
                null
            ).run {
                this as MethodNode
                visitVarInsn(Opcodes.ALOAD, 0)
                visitInsn(Opcodes.DUP)
                visitMethodInsn(Opcodes.INVOKESPECIAL, clazz.superName, "<init>", "()V", false)
                node.refs.forEachIndexed { i, it ->
                    visitInsn(Opcodes.DUP)
                    load(it.type.name, i + 1, this)
                    visitFieldInsn(Opcodes.PUTFIELD, clazz.name, it.name, it.type.desc)
                    type.fields += VirtualFieldImpl(type, it.name, it.type, isStatic = false, isEnum = false)
                    clazz.visitField(Opcodes.ACC_PRIVATE, it.name, it.type.desc, null, null)
                }
                visitInsn(Opcodes.RETURN)
                this
            }
            val method = findLambdaMethod(node.type!!)
            clazz.visitMethod(
                Opcodes.ACC_PUBLIC + Opcodes.ACC_FINAL,
                method.name,
                method.desc,
                null,
                null
            ).run {
                this as MethodNode
                visit(node, method, compiler, ctx.with(ClassContext(clazz, type)))
            }
            ctx.method.node.run {
                visitTypeInsn(Opcodes.NEW, clazz.name)
                visitInsn(Opcodes.DUP)
                node.refs.forEach { it.load(this) }
                visitMethodInsn(Opcodes.INVOKESPECIAL, clazz.name, "<init>", ctor.desc, false)
            }
            return Variable.tmp(node, node.type)
        }
    }
}