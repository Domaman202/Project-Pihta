package ru.DmN.pht.base.compiler.java.compilers

import ru.DmN.pht.base.ast.NodeProcessedUse
import ru.DmN.pht.base.compiler.java.Compiler
import ru.DmN.pht.base.compiler.java.utils.CompilationContext
import ru.DmN.pht.base.utils.Variable

object NCUseCtx : INodeCompiler<NodeProcessedUse> {
    override fun compile(node: NodeProcessedUse, compiler: Compiler, ctx: CompilationContext) {
        val context = NCUse.injectModules(node, compiler, ctx)
        node.exports.forEach { NCDefault.compile(it, compiler, ctx) }
        NCDefault.compile(node, compiler, context)
    }

    override fun compileVal(node: NodeProcessedUse, compiler: Compiler, ctx: CompilationContext): Variable {
        val context = NCUse.injectModules(node, compiler, ctx)
        node.exports.forEach { NCDefault.compile(it, compiler, ctx) }
        return NCDefault.compileVal(node, compiler, context)
    }
}