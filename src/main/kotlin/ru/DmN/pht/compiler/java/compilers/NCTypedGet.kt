package ru.DmN.pht.compiler.java.compilers

import ru.DmN.pht.ast.NodeTypedGet
import ru.DmN.pht.std.compiler.java.utils.body
import ru.DmN.siberia.Compiler
import ru.DmN.siberia.compiler.ctx.CompilationContext
import ru.DmN.siberia.compilers.INodeCompiler
import ru.DmN.siberia.utils.Variable

object NCTypedGet : INodeCompiler<NodeTypedGet> {
    override fun compileVal(node: NodeTypedGet, compiler: Compiler, ctx: CompilationContext): Variable =
        ctx.body.variables.find { it.type() == node.type }!!
}