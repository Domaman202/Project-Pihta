package ru.DmN.pht.std.compiler.java.compilers

import ru.DmN.pht.base.compiler.java.Compiler
import ru.DmN.pht.base.compiler.java.compilers.NodeCompiler
import ru.DmN.pht.base.compiler.java.ctx.CompilationContext
import ru.DmN.pht.std.ast.NodeHelper

object NCHelper : NodeCompiler<NodeHelper>() {
    override fun compute(node: NodeHelper, compiler: Compiler, ctx: CompilationContext, name: Boolean): Any? =
        node.calcValue(node, compiler, ctx, name)
}