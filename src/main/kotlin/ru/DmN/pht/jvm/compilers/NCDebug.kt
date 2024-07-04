package ru.DmN.pht.jvm.compilers

import ru.DmN.siberia.ast.Node
import ru.DmN.siberia.compiler.Compiler
import ru.DmN.siberia.compiler.ctx.CompilationContext
import ru.DmN.siberia.compilers.INodeCompiler
import ru.DmN.siberia.utils.Variable
import ru.DmN.siberia.utils.exception.MessageException

object NCDebug : INodeCompiler<Node> {
    override fun compile(node: Node, compiler: Compiler, ctx: CompilationContext) {
        throw MessageException(null, "Debug (no val)")
    }

    override fun compileVal(node: Node, compiler: Compiler, ctx: CompilationContext): Variable {
        throw MessageException(null, "Debug (val)")
    }
}