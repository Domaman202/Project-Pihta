package ru.DmN.pht.jvm.compilers

import org.objectweb.asm.Label
import ru.DmN.pht.compiler.java.ctx.BodyContext
import ru.DmN.pht.jvm.compiler.ctx.bodyOrNull
import ru.DmN.pht.jvm.compiler.ctx.method
import ru.DmN.pht.jvm.compiler.ctx.with
import ru.DmN.siberia.ast.NodeNodesList
import ru.DmN.siberia.compiler.Compiler
import ru.DmN.siberia.compiler.ctx.CompilationContext
import ru.DmN.siberia.compilers.INodeCompiler
import ru.DmN.siberia.compilers.NCDefault
import ru.DmN.siberia.utils.Variable

object NCBody : INodeCompiler<NodeNodesList> {
    override fun compile(node: NodeNodesList, compiler: Compiler, ctx: CompilationContext) =
        NCDefault.compile(node, compiler, ctxOf(ctx))

    override fun compileVal(node: NodeNodesList, compiler: Compiler, ctx: CompilationContext): Variable =
        NCDefault.compileVal(node, compiler, ctxOf(ctx))

    private fun ctxOf(ctx: CompilationContext): CompilationContext {
        val start = Label()
        ctx.method.node.visitLabel(start)
        val body = BodyContext.of(ctx.bodyOrNull, start)
        return ctx.with(body).apply {
            val stop = Label()
            ctx.method.node.visitLabel(stop)
            body.stop = stop
        }
    }
}