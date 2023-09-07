package ru.DmN.pht.std.module.compiler.java.compilers

import ru.DmN.pht.base.compiler.java.Compiler
import ru.DmN.pht.base.compiler.java.compilers.INodeCompiler
import ru.DmN.pht.base.compiler.java.ctx.CompilationContext
import ru.DmN.pht.base.utils.Variable
import ru.DmN.pht.std.module.ast.NodeModule

object NCModule : INodeCompiler<NodeModule> {
    override fun compile(node: NodeModule, compiler: Compiler, ctx: CompilationContext, ret: Boolean): Variable? {
        ctx.modules += node.module
        return null
    }
}