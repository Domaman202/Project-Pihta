package ru.DmN.pht.base.compiler.java.compilers

import ru.DmN.pht.base.ast.NodeProcessedUse
import ru.DmN.pht.base.compiler.java.Compiler
import ru.DmN.pht.base.compiler.java.utils.CompilationContext
import ru.DmN.pht.base.utils.Module
import ru.DmN.pht.base.utils.Variable

object NCUse : INodeCompiler<NodeProcessedUse> {
    override fun compile(node: NodeProcessedUse, compiler: Compiler, ctx: CompilationContext) =
        NCDefault.compile(node, compiler, injectModules(node, compiler, ctx))

    override fun compileVal(node: NodeProcessedUse, compiler: Compiler, ctx: CompilationContext): Variable =
        NCDefault.compileVal(node, compiler, injectModules(node, compiler, ctx))

    fun injectModules(node: NodeProcessedUse, compiler: Compiler, ctx: CompilationContext): CompilationContext {
        val context = ctx.subCtx()
        node.names.forEach{ Module.MODULES[it]!!.inject(compiler, context) }
        node.processed.forEach { compiler.compile(it, context) }
        return context
    }
}