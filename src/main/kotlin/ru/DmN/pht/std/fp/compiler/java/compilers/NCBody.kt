package ru.DmN.pht.std.fp.compiler.java.compilers

import org.objectweb.asm.Label
import ru.DmN.pht.base.compiler.java.Compiler
import ru.DmN.pht.base.compiler.java.compilers.INodeCompiler
import ru.DmN.pht.base.compiler.java.compilers.NCDefault
import ru.DmN.pht.base.compiler.java.utils.CompilationContext
import ru.DmN.pht.base.parser.ast.NodeNodesList
import ru.DmN.pht.base.utils.Variable
import ru.DmN.pht.std.base.compiler.java.ctx.BodyContext
import ru.DmN.pht.std.base.compiler.java.utils.bodyOrNull
import ru.DmN.pht.std.base.compiler.java.utils.method
import ru.DmN.pht.std.base.compiler.java.utils.with

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