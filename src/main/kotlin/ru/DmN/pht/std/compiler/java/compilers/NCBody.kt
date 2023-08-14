package ru.DmN.pht.std.compiler.java.compilers

import org.objectweb.asm.Label
import ru.DmN.pht.base.Compiler
import ru.DmN.pht.base.compiler.java.compilers.NodeCompiler
import ru.DmN.pht.base.compiler.java.ctx.BodyContext
import ru.DmN.pht.base.compiler.java.ctx.CompilationContext
import ru.DmN.pht.base.parser.ast.NodeNodesList
import ru.DmN.pht.base.utils.Variable
import ru.DmN.pht.base.utils.VirtualType

object NCBody : NodeCompiler<NodeNodesList>() {
    override fun calcType(node: NodeNodesList, compiler: Compiler, ctx: CompilationContext): VirtualType? =
        if (ctx.type.method)
            compiler.calc(node.nodes.last(), ctxOf(ctx))
        else null

    override fun compile(node: NodeNodesList, compiler: Compiler, ctx: CompilationContext, ret: Boolean): Variable? =
        if (ctx.type.method) {
            val context = ctxOf(ctx)
            node.nodes.dropLast(1).forEach { compiler.compile(it, context, false) }
            compiler.compile(node.nodes.last(), context, ret).apply {
                val stopLabel = Label()
                ctx.mctx!!.node.visitLabel(stopLabel)
                context.bctx!!.stopLabel = stopLabel
            }
        } else null

    fun ctxOf(ctx: CompilationContext): CompilationContext =
        if (ctx.type.body)
            ctx.with(BodyContext.of(ctx.bctx!!))
        else if (ctx.type.method)
            ctx.with(BodyContext.of(ctx.mctx!!))
        else throw RuntimeException()
}