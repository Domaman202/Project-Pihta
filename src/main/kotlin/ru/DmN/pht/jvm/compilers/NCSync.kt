package ru.DmN.pht.jvm.compilers

import jdk.internal.org.objectweb.asm.Opcodes
import org.objectweb.asm.Label
import ru.DmN.pht.compiler.java.utils.load
import ru.DmN.pht.jvm.ast.NodeSync
import ru.DmN.pht.jvm.compiler.ctx.body
import ru.DmN.pht.jvm.compiler.ctx.method
import ru.DmN.siberia.compiler.Compiler
import ru.DmN.siberia.compiler.ctx.CompilationContext
import ru.DmN.siberia.compilers.INodeCompiler
import ru.DmN.siberia.compilers.NCDefault
import ru.DmN.siberia.utils.Variable

object NCSync : INodeCompiler<NodeSync> {
    override fun compile(node: NodeSync, compiler: Compiler, ctx: CompilationContext) =
        compile(node, NCDefault::compile, compiler, ctx)

    override fun compileVal(node: NodeSync, compiler: Compiler, ctx: CompilationContext): Variable =
        compile(node, NCDefault::compileVal, compiler, ctx)

    private fun <T> compile(node: NodeSync, compile: (node: NodeSync, compiler: Compiler, ctx: CompilationContext) -> T, compiler: Compiler, ctx: CompilationContext): T {
        ctx.method.node.run {
            val start = Label()
            val end = Label()
            load(compiler.compileVal(node.lock, ctx), this)
            val id = ctx.body.lvi.getAndIncrement()
            visitLocalVariable(Variable.tmp(node), "Ljava/lang/Object;", null, start, end, id)
            visitVarInsn(Opcodes.ASTORE, id)
            visitVarInsn(Opcodes.ALOAD, id)
            visitInsn(Opcodes.MONITORENTER)
            visitLabel(start)
            return compile(node, compiler, ctx).apply {
                visitLabel(end)
                visitVarInsn(Opcodes.ALOAD, id)
                visitInsn(Opcodes.MONITOREXIT)
            }
        }
    }
}