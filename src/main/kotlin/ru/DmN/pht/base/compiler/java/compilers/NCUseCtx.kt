package ru.DmN.pht.base.compiler.java.compilers

import ru.DmN.pht.base.compiler.java.Compiler
import ru.DmN.pht.base.compiler.java.ctx.CompilationContext
import ru.DmN.pht.base.parser.ast.NodeUseCtx
import ru.DmN.pht.base.utils.Module
import ru.DmN.pht.base.utils.Variable
import ru.DmN.pht.base.utils.VirtualType
import ru.DmN.pht.std.base.compiler.java.utils.SubList
import ru.DmN.pht.std.base.compiler.java.utils.SubMap

object NCUseCtx : INodeCompiler<NodeUseCtx> {
    override fun calc(node: NodeUseCtx, compiler: Compiler, ctx: CompilationContext): VirtualType? {
        val context = CompilationContext(ctx.stage, SubList(ctx.modules), SubMap(ctx.contexts))
        node.names.map { Module.MODULES[it]!!.inject(compiler, context) }
        return NCDefault.calc(node, compiler, ctx)
    }

    override fun compile(node: NodeUseCtx, compiler: Compiler, ctx: CompilationContext, ret: Boolean): Variable? {
        val context = CompilationContext(ctx.stage, SubList(ctx.modules), SubMap(ctx.contexts))
        node.names.map { Module.MODULES[it]!!.inject(compiler, context, ret) }
        return NCDefault.compile(node, compiler, ctx, ret)
    }
}