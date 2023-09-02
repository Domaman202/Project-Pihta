package ru.DmN.pht.std.base.compiler.java.compilers

import ru.DmN.pht.base.compiler.java.Compiler
import ru.DmN.pht.base.compiler.java.compilers.NCDefault
import ru.DmN.pht.base.compiler.java.ctx.CompilationContext
import ru.DmN.pht.base.parser.ast.NodeNodesList
import ru.DmN.pht.base.utils.Variable
import ru.DmN.pht.base.utils.VirtualType
import ru.DmN.pht.std.base.compiler.java.utils.computeName
import ru.DmN.pht.std.base.compiler.java.utils.global
import ru.DmN.pht.std.base.compiler.java.utils.with

object NCSubNs : IStdNodeCompiler<NodeNodesList> {
    override fun calc(node: NodeNodesList, compiler: Compiler, ctx: CompilationContext): VirtualType? =
        super.calc(node, compiler, process(node, compiler, ctx))

    override fun compile(node: NodeNodesList, compiler: Compiler, ctx: CompilationContext, ret: Boolean): Variable? =
        NCDefault.compile(node, compiler, process(node, compiler, ctx), ret)

    private fun process(node: NodeNodesList, compiler: Compiler, ctx: CompilationContext): CompilationContext {
        val gctx = ctx.global
        return ctx.with(gctx.with(gctx.name(compiler.computeName(node.nodes.first(), ctx))))
    }
}