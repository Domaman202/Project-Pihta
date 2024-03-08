package ru.DmN.pht.compiler.java.compilers

import ru.DmN.pht.compiler.java.utils.load
import ru.DmN.pht.jvm.compiler.ctx.method
import ru.DmN.pht.jvm.compiler.ctx.returnHook
import ru.DmN.siberia.ast.NodeNodesList
import ru.DmN.siberia.compiler.Compiler
import ru.DmN.siberia.compiler.ctx.CompilationContext
import ru.DmN.siberia.compilers.INodeCompiler
import ru.DmN.siberia.utils.Variable

object NCRet : INodeCompiler<NodeNodesList> {
    override fun compile(node: NodeNodesList, compiler: Compiler, ctx: CompilationContext) {
        node.nodes.firstOrNull()?.let { load(compiler.compileVal(it, ctx), ctx.method.node) }
        ctx.returnHook()
    }

    override fun compileVal(node: NodeNodesList, compiler: Compiler, ctx: CompilationContext): Variable =
        compiler.compileVal(node.nodes[0], ctx)
}