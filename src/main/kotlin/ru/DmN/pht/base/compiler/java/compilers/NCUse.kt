package ru.DmN.pht.base.compiler.java.compilers

import ru.DmN.pht.base.ast.NodeProcessedUse
import ru.DmN.pht.base.compiler.java.Compiler
import ru.DmN.pht.base.compiler.java.utils.CompilationContext
import ru.DmN.pht.base.utils.Module
import ru.DmN.pht.base.utils.Variable

object NCUse : INodeCompiler<NodeProcessedUse> {
    override fun compile(node: NodeProcessedUse, compiler: Compiler, ctx: CompilationContext) {
        injectModules(node, compiler, ctx)
        node.exports.forEach { NCDefault.compile(it, compiler, ctx) }
        NCDefault.compile(node, compiler, ctx)
    }

    override fun compileVal(node: NodeProcessedUse, compiler: Compiler, ctx: CompilationContext): Variable {
        injectModules(node, compiler, ctx)
        node.exports.forEach { NCDefault.compile(it, compiler, ctx) }
        return NCDefault.compileVal(node, compiler, ctx)
    }

    fun injectModules(node: NodeProcessedUse, compiler: Compiler, ctx: CompilationContext) {
        node.names.forEach{ Module.getOrThrow(it).load(compiler, ctx) }
        node.processed.forEach { compiler.compile(it, ctx) }
    }
}