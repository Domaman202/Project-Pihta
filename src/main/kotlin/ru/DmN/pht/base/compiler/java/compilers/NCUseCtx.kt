package ru.DmN.pht.base.compiler.java.compilers

import ru.DmN.pht.base.compiler.java.Compiler
import ru.DmN.pht.base.compiler.java.utils.CompilationContext
import ru.DmN.pht.base.parser.ast.NodeUse
import ru.DmN.pht.base.utils.Variable

object NCUseCtx : INodeCompiler<NodeUse> {
    override fun compile(node: NodeUse, compiler: Compiler, ctx: CompilationContext) {
        val context = ctx.subCtx()
        NCUse.injectModules(node, compiler, context)
        NCUse.compile(node, compiler, context)
    }

    override fun compileVal(node: NodeUse, compiler: Compiler, ctx: CompilationContext): Variable {
        val context = ctx.subCtx()
        NCUse.injectModules(node, compiler, context)
        return NCUse.compileVal(node, compiler, context)
    }
}