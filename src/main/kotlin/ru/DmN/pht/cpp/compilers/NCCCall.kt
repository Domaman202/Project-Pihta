package ru.DmN.pht.cpp.compilers

import ru.DmN.pht.cpp.compiler.utils.name
import ru.DmN.pht.cpp.compilers.NCMCall.compileArgs
import ru.DmN.pht.jvm.utils.vtype.superclass
import ru.DmN.pht.processor.ctx.method
import ru.DmN.siberia.ast.NodeNodesList
import ru.DmN.siberia.compiler.Compiler
import ru.DmN.siberia.compiler.ctx.CompilationContext

object NCCCall : ICppNRCompiler<NodeNodesList> {
    override fun StringBuilder.compile(node: NodeNodesList, compiler: Compiler, ctx: CompilationContext) {
        ctx.method.declaringClass.run {
            append("new (this) ").append(superclass!!.name())
            compileArgs(node, compiler, ctx)
            append(";\nnew (this) ").append(name()).append("(nullptr)")
        }
    }
}