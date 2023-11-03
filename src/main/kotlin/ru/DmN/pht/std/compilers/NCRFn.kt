package ru.DmN.pht.std.compilers

import org.objectweb.asm.Opcodes
import org.objectweb.asm.tree.ClassNode
import org.objectweb.asm.tree.MethodNode
import ru.DmN.pht.base.compiler.java.Compiler
import ru.DmN.pht.base.compiler.java.compilers.INodeCompiler
import ru.DmN.pht.base.compiler.java.utils.CompilationContext
import ru.DmN.pht.base.utils.Variable
import ru.DmN.pht.base.utils.VirtualField
import ru.DmN.pht.base.utils.VirtualType
import ru.DmN.pht.std.ast.NodeRFn
import ru.DmN.pht.std.compiler.java.ctx.ClassContext
import ru.DmN.pht.std.compiler.java.utils.load
import ru.DmN.pht.std.compiler.java.utils.method
import ru.DmN.pht.std.compiler.java.utils.with
import ru.DmN.pht.std.compilers.NCDefn.visit
import ru.DmN.pht.std.utils.NVC
import ru.DmN.pht.std.utils.findLambdaMethod
import kotlin.math.absoluteValue

object NCRFn : INodeCompiler<NodeRFn> {
    override fun compileVal(node: NodeRFn, compiler: Compiler, ctx: CompilationContext): Variable {
        val type = VirtualType(node.name, mutableListOf(node.type!!))
        compiler.tp.types += type
        val clazz = ClassNode()
        compiler.classes[node.name] = clazz
        clazz.visit(
            Opcodes.V20,
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
                type.fields += VirtualField(type, it.name, it.type, static = false, enum = false)
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