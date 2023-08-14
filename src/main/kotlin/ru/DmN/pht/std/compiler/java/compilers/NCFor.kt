package ru.DmN.pht.std.compiler.java.compilers

import org.objectweb.asm.Label
import org.objectweb.asm.Opcodes
import ru.DmN.pht.base.Compiler
import ru.DmN.pht.base.compiler.java.compilers.NodeCompiler
import ru.DmN.pht.base.compiler.java.ctx.CompilationContext
import ru.DmN.pht.base.utils.Variable
import ru.DmN.pht.std.ast.NodeFor
import ru.DmN.pht.std.utils.load

object NCFor : NodeCompiler<NodeFor>() {
    override fun compile(node: NodeFor, compiler: Compiler, ctx: CompilationContext, ret: Boolean): Variable? {
        if (ctx.type.method && ctx.type.body) {
            ctx.mctx!!.node.run {
                val labelInit = Label()
                visitLabel(labelInit)
                val iterator = ctx.mctx.createVariable(ctx.bctx!!, "pht$${node.hashCode()}", "java.util.Iterator", labelInit)
                val type = ctx.gctx.getType(compiler, compiler.compile(node.nodes.first(), ctx, true)!!.apply { load(this, this@run) }.type!!)
                if (!type.isAssignableFrom(compiler.typeOf("java.util.Iterator"))) {
                    if (type.isAssignableFrom(compiler.typeOf("java.lang.Iterable")))
                        visitMethodInsn(Opcodes.INVOKEINTERFACE, "java/lang/Iterable", "iterator", "()Ljava/util/Iterator;", true)
                    else throw RuntimeException()
                }
                visitVarInsn(Opcodes.ASTORE, iterator.id)
                val labelStart = Label()
                visitLabel(labelStart)
                visitVarInsn(Opcodes.ALOAD, iterator.id)
                visitInsn(Opcodes.DUP)
                visitMethodInsn(Opcodes.INVOKEINTERFACE, "java/util/Iterator", "hasNext", "()Z", true)
                val labelExit = Label()
                visitJumpInsn(Opcodes.IFEQ, labelExit)
                val labelCycle = Label()
                visitLabel(labelCycle)
                val value = ctx.mctx.createVariable(ctx.bctx, node.name, "java.lang.Object", labelCycle)
                visitMethodInsn(Opcodes.INVOKEINTERFACE, "java/util/Iterator", "next", "()Ljava/lang/Object;", true)
                visitVarInsn(Opcodes.ASTORE, value.id)
                node.nodes.drop(1).forEach { compiler.compile(it, ctx, false) }
                visitJumpInsn(Opcodes.GOTO, labelStart)
                visitLabel(labelExit)
                visitInsn(Opcodes.POP)
            }
        }
        return null
    }
}