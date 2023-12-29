package ru.DmN.pht.std.compiler.java.compilers

import org.objectweb.asm.Label
import org.objectweb.asm.Opcodes
import ru.DmN.pht.std.ast.NodeCatch
import ru.DmN.pht.std.compiler.java.ctx.BodyContext
import ru.DmN.pht.std.compiler.java.utils.body
import ru.DmN.pht.std.compiler.java.utils.method
import ru.DmN.pht.std.compiler.java.utils.with
import ru.DmN.siberia.Compiler
import ru.DmN.siberia.ast.Node
import ru.DmN.siberia.compiler.ctx.CompilationContext
import ru.DmN.siberia.compilers.INodeCompiler
import ru.DmN.siberia.compilers.NCDefault
import ru.DmN.siberia.utils.Variable

object NCCatch : INodeCompiler<NodeCatch> {
    override fun compile(node: NodeCatch, compiler: Compiler, ctx: CompilationContext) =
        compile(node, compiler, ctx, NCDefault::compile) { it, context -> compiler.compile(it, context) }

    override fun compileVal(node: NodeCatch, compiler: Compiler, ctx: CompilationContext): Variable =
        compile(node, compiler, ctx, NCDefault::compileVal) { it, context -> compiler.compileVal(it, context) }

    private fun <T> compile(node: NodeCatch, compiler: Compiler, ctx: CompilationContext, compileBody: (node: NodeCatch, compiler: Compiler, ctx: CompilationContext) -> T, compileCatcher: (node: Node, ctx: CompilationContext) -> T): T {
        ctx.method.node.run {
            val tryStart = Label()
            val tryStop = Label()
            val allCatchStop = Label()
            visitLabel(tryStart)
            val result = compileBody(node, compiler, ctx)
            visitLabel(tryStop)
            visitJumpInsn(Opcodes.GOTO, allCatchStop)
            val bctx = ctx.body
            node.catchers.forEach {
                val handle = Label()
                visitLabel(handle)
                if (it.third != null) {
                    val body = BodyContext.of(bctx, handle)
                    body.add(it.first, it.second)
                    compileCatcher(it.third!!, ctx.with(body))
                    val handleStop = Label()
                    visitLabel(handleStop)
                    body.stop = handleStop
                }
                visitJumpInsn(Opcodes.GOTO, allCatchStop)
                visitTryCatchBlock(tryStart, tryStop, handle, it.second.className)
            }
            visitLabel(allCatchStop)
            return result
        }
    }
}