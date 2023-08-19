package ru.DmN.pht.std.compiler.java.compilers

import ru.DmN.pht.base.compiler.java.Compiler
import ru.DmN.pht.base.compiler.java.ctx.CompilationContext
import ru.DmN.pht.base.parser.ast.NodeNodesList
import ru.DmN.pht.base.utils.Variable
import ru.DmN.pht.std.compiler.java.computeName
import ru.DmN.pht.std.utils.Module

object NCUse : IStdNodeCompiler<NodeNodesList> {
    override fun compile(node: NodeNodesList, compiler: Compiler, ctx: CompilationContext, ret: Boolean): Variable? =
        node.nodes.map { compiler.computeName(it, ctx) }.map { Module.MODULES[it]!!.inject(compiler, ctx, ret) }.last()
}