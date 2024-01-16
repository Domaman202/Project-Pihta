package ru.DmN.pht.compiler.java.compilers

import jdk.internal.org.objectweb.asm.Opcodes
import org.objectweb.asm.Label
import ru.DmN.pht.ast.NodeSync
import ru.DmN.pht.std.compiler.java.utils.body
import ru.DmN.pht.std.compiler.java.utils.method
import ru.DmN.siberia.Compiler
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
            val id = compiler.compileVal(node.lock, ctx).let {
                if (it.tmp) {
                    visitInsn(Opcodes.DUP)
                    val id = ctx.body.lvi.getAndIncrement()
                    visitLocalVariable(Variable.tmp(node), "Ljava/lang/Object;", null, start, end, id)
                    visitVarInsn(Opcodes.ASTORE, id)
                    id
                } else {
                    visitVarInsn(Opcodes.ALOAD, it.id)
                    it.id
                }
            }
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