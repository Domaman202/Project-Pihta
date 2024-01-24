package ru.DmN.pht.std.compiler.java.compilers

import ru.DmN.pht.std.compiler.java.utils.load
import ru.DmN.pht.std.compiler.java.utils.method
import ru.DmN.siberia.Compiler
import ru.DmN.siberia.ast.NodeNodesList
import ru.DmN.siberia.compiler.ctx.CompilationContext
import ru.DmN.siberia.compilers.INodeCompiler
import ru.DmN.siberia.utils.Variable

object NCRet : INodeCompiler<NodeNodesList> {
    override fun compile(node: NodeNodesList, compiler: Compiler, ctx: CompilationContext) {
        val mctx = ctx.method
        node.nodes.firstOrNull()?.let { load(compiler.compileVal(it, ctx), mctx.node) }
        mctx.returnHook(compiler, ctx)
    }

    override fun compileVal(node: NodeNodesList, compiler: Compiler, ctx: CompilationContext): Variable =
        compiler.compileVal(node.nodes[0], ctx)
}