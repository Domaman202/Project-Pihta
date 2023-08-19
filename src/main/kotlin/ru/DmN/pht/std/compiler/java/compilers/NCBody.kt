package ru.DmN.pht.std.compiler.java.compilers

import org.objectweb.asm.Label
import ru.DmN.pht.base.compiler.java.Compiler
import ru.DmN.pht.base.compiler.java.ctx.CompilationContext
import ru.DmN.pht.base.parser.ast.NodeNodesList
import ru.DmN.pht.base.utils.Variable
import ru.DmN.pht.base.utils.VirtualType
import ru.DmN.pht.std.compiler.java.ctx.*
import ru.DmN.pht.std.compiler.java.utils.*

object NCBody : IStdNodeCompiler<NodeNodesList> {
    override fun calc(node: NodeNodesList, compiler: Compiler, ctx: CompilationContext): VirtualType? =
        compiler.calc(node.nodes.last(), ctxOf(ctx))

    override fun compile(node: NodeNodesList, compiler: Compiler, ctx: CompilationContext, ret: Boolean): Variable? {
        val context = ctxOf(ctx)
        node.nodes.dropLast(1).forEach { compiler.compile(it, context, false) }
        return compiler.compile(node.nodes.last(), context, ret).apply {
            val stopLabel = Label()
            ctx.method.node.visitLabel(stopLabel)
            context.body.stopLabel = stopLabel
        }
    }

    fun ctxOf(ctx: CompilationContext): CompilationContext =
        if (ctx.isBody())
            ctx.with(BodyContext.of(ctx.body))
        else if (ctx.isMethod())
            ctx.with(BodyContext.of(ctx.method))
        else throw RuntimeException()
}