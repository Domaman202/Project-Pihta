package ru.DmN.pht.jvm.compilers

import ru.DmN.pht.ast.NodeBGet
import ru.DmN.siberia.compiler.Compiler
import ru.DmN.siberia.compiler.ctx.CompilationContext
import ru.DmN.siberia.utils.Variable

object NCBGet : IValueNodeCompiler<NodeBGet> {
    override fun compileVal(node: NodeBGet, compiler: Compiler, ctx: CompilationContext): Variable =
        node.variable
}