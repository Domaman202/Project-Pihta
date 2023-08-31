package ru.DmN.pht.base.compiler.java.compilers

import ru.DmN.pht.base.Compiler
import ru.DmN.pht.base.compiler.java.ctx.CompilationContext
import ru.DmN.pht.base.parser.ast.NodeUse
import ru.DmN.pht.base.utils.Module
import ru.DmN.pht.base.utils.Variable

object NCUse : INodeCompiler<NodeUse> {
    override fun compile(node: NodeUse, compiler: Compiler, ctx: CompilationContext, ret: Boolean): Variable? =
        node.names.map { Module.MODULES[it]!!.inject(compiler, ctx, ret) }.last()
}