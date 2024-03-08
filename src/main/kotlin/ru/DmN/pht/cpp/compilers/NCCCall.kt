package ru.DmN.pht.cpp.compilers

import ru.DmN.pht.cpp.compiler.utils.name
import ru.DmN.pht.cpp.compilers.NCMCall.compileArgs
import ru.DmN.pht.processor.ctx.method
import ru.DmN.siberia.ast.NodeNodesList
import ru.DmN.siberia.compiler.Compiler
import ru.DmN.siberia.compiler.ctx.CompilationContext

object NCCCall : ICppCompiler<NodeNodesList> {
    override fun StringBuilder.compile(node: NodeNodesList, compiler: Compiler, ctx: CompilationContext) {
        append("new (this) ").append(ctx.method.declaringClass.superclass!!.name())
        compileArgs(node, compiler, ctx)
    }
}