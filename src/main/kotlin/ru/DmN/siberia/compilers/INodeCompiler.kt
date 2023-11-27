package ru.DmN.siberia.compilers

import ru.DmN.siberia.Compiler
import ru.DmN.siberia.compiler.ctx.CompilationContext
import ru.DmN.siberia.ast.Node
import ru.DmN.siberia.utils.Variable

/**
 * Компилятор ноды.
 */
interface INodeCompiler<T : Node> {
    /**
     * Компилирует ноду без значения
     */
    fun compile(node: T, compiler: Compiler, ctx: CompilationContext) {
        throw UnsupportedOperationException()
    }

    /**
     * Компилирует ноду со значением
     */
    fun compileVal(node: T, compiler: Compiler, ctx: CompilationContext): Variable {
        throw UnsupportedOperationException()
    }
}