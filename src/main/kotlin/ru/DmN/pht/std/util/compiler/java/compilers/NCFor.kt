package ru.DmN.pht.std.util.compiler.java.compilers

import org.objectweb.asm.Label
import org.objectweb.asm.Opcodes
import ru.DmN.pht.base.compiler.java.Compiler
import ru.DmN.pht.base.compiler.java.compilers.NodeCompiler
import ru.DmN.pht.base.compiler.java.ctx.CompilationContext
import ru.DmN.pht.base.parser.ast.Node
import ru.DmN.pht.base.parser.ast.NodeNodesList
import ru.DmN.pht.base.utils.Variable
import ru.DmN.pht.std.utils.load

object NCFor : NodeCompiler<NodeNodesList>() {
    override fun compile(node: NodeNodesList, compiler: Compiler, ctx: CompilationContext, ret: Boolean): Variable? {
        if (ctx.type.method && ctx.type.body) {
            ctx.method!!.node.run {
                val pair = compiler.compute<List<Node>>(node.nodes.first(), ctx, false)
                val labelInit = Label()
                visitLabel(labelInit)
                val iterator = ctx.method.createVariable(ctx.body!!, "pht$${node.hashCode()}", "java.util.Iterator", labelInit)
                val type = ctx.global.getType(compiler, compiler.compile(pair.last(), ctx, true)!!.apply { load(this, this@run) }.type!!)
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
                val value = ctx.method.createVariable(ctx.body, compiler.computeStringConst(pair.first(), ctx), "java.lang.Object", labelCycle)
                visitMethodInsn(Opcodes.INVOKEINTERFACE, "java/util/Iterator", "next", "()Ljava/lang/Object;", true)
                visitVarInsn(Opcodes.ASTORE, value.id)
                compiler.compile(node.nodes.last(), ctx, false)
                visitJumpInsn(Opcodes.GOTO, labelStart)
                visitLabel(labelExit)
                visitInsn(Opcodes.POP)
            }
        }
        return null
    }
}