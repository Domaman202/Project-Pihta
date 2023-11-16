package ru.DmN.pht.std.compiler.java.compilers

import org.objectweb.asm.Opcodes
import org.objectweb.asm.tree.ClassNode
import org.objectweb.asm.tree.MethodNode
import ru.DmN.pht.base.compiler.java.Compiler
import ru.DmN.pht.base.compiler.java.compilers.INodeCompiler
import ru.DmN.pht.base.compiler.java.utils.CompilationContext
import ru.DmN.pht.base.utils.Variable
import ru.DmN.pht.base.utils.VirtualType.VirtualTypeImpl
import ru.DmN.pht.std.ast.NodeFn
import ru.DmN.pht.std.compiler.java.compilers.NCDefn.visit
import ru.DmN.pht.std.compiler.java.ctx.ClassContext
import ru.DmN.pht.std.compiler.java.utils.method
import ru.DmN.pht.std.compiler.java.utils.with
import ru.DmN.pht.std.utils.findLambdaMethod
import kotlin.math.absoluteValue

object NCFn : INodeCompiler<NodeFn> {
    override fun compileVal(node: NodeFn, compiler: Compiler, ctx: CompilationContext): Variable {
        val name = "PhtLambda${node.hashCode().absoluteValue}"
        val clazz = ClassNode()
        compiler.classes[name] = clazz
        clazz.visit(
            Opcodes.V19,
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
    }
}