package ru.DmN.pht.jvm.compilers

import ru.DmN.siberia.Compiler
import ru.DmN.siberia.ast.Node
import ru.DmN.siberia.compiler.ctx.CompilationContext
import ru.DmN.siberia.compilers.INodeCompiler
import ru.DmN.siberia.utils.Variable

interface IStdNodeCompiler<T : Node, R> : INodeCompiler<T> {
    fun compileAsm(node: T, compiler: Compiler, ctx: CompilationContext): R {
        throw UnsupportedOperationException()
    }

    fun compileValAsm(node: T, compiler: Compiler, ctx: CompilationContext): Pair<Variable, R> {
        throw UnsupportedOperationException()
    }
}