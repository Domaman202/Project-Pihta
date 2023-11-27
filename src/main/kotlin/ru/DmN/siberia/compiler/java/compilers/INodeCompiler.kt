package ru.DmN.siberia.compiler.java.compilers

import ru.DmN.siberia.compiler.java.Compiler
import ru.DmN.siberia.compiler.java.utils.CompilationContext
import ru.DmN.siberia.ast.Node
import ru.DmN.siberia.utils.Variable

interface INodeCompiler<T : Node> {
    fun compile(node: T, compiler: Compiler, ctx: CompilationContext) {
        throw UnsupportedOperationException()
    }

    fun compileVal(node: T, compiler: Compiler, ctx: CompilationContext): Variable {
        throw UnsupportedOperationException()
    }
}