package ru.DmN.pht.base.compiler.java.compilers

import ru.DmN.pht.base.compiler.java.Compiler
import ru.DmN.pht.base.compiler.java.utils.CompilationContext
import ru.DmN.pht.base.parser.ast.NodeUse
import ru.DmN.pht.base.utils.Module
import ru.DmN.pht.base.utils.Variable

object NCUse : INodeCompiler<NodeUse> {
    override fun compile(node: NodeUse, compiler: Compiler, ctx: CompilationContext) {
        injectModules(node, compiler, ctx)
        NCDefault.compile(node, compiler, ctx)
    }

    override fun compileVal(node: NodeUse, compiler: Compiler, ctx: CompilationContext): Variable {
        injectModules(node, compiler, ctx)
        return NCDefault.compileVal(node, compiler, ctx)
    }

    fun injectModules(node: NodeUse, compiler: Compiler, ctx: CompilationContext) =
        node.names.forEachIndexed { i, it -> Module.MODULES[it]!!.inject(compiler, ctx) }
}