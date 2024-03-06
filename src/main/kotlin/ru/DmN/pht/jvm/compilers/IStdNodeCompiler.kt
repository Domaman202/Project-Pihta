package ru.DmN.pht.jvm.compilers

import ru.DmN.siberia.ast.Node
import ru.DmN.siberia.compiler.Compiler
import ru.DmN.siberia.compiler.ctx.CompilationContext
import ru.DmN.siberia.compilers.INodeCompiler
import ru.DmN.siberia.utils.Variable

interface IStdNodeCompiler<T : Node, A, V> : INodeCompiler<T> {
    fun compileAsm(node: T, compiler: Compiler, ctx: CompilationContext): A {
        throw UnsupportedOperationException()
    }

    fun compileValAsm(node: T, compiler: Compiler, ctx: CompilationContext): Pair<Variable, A> {
        throw UnsupportedOperationException()
    }

    fun computeValue(node: T, compiler: Compiler, ctx: CompilationContext): V {
        throw UnsupportedOperationException()
    }
}