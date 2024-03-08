package ru.DmN.pht.cpp.compilers

import ru.DmN.pht.cpp.compiler.ctx.out
import ru.DmN.siberia.ast.Node
import ru.DmN.siberia.compiler.Compiler
import ru.DmN.siberia.compiler.ctx.CompilationContext
import ru.DmN.siberia.compilers.INodeCompiler
import ru.DmN.siberia.utils.Variable

interface ICppCompiler<T : Node> : INodeCompiler<T> {
    override fun compile(node: T, compiler: Compiler, ctx: CompilationContext) =
        compiler.contexts.out.compile(node, compiler, ctx)

    override fun compileVal(node: T, compiler: Compiler, ctx: CompilationContext): Variable =
        compiler.contexts.out.compileVal(node, compiler, ctx)

    fun StringBuilder.compile(node: T, compiler: Compiler, ctx: CompilationContext) {
        compileVal(node, compiler, ctx)
    }

    fun StringBuilder.compileVal(node: T, compiler: Compiler, ctx: CompilationContext): Variable {
        throw UnsupportedOperationException()
    }
}