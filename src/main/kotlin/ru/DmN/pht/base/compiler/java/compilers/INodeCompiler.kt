package ru.DmN.pht.base.compiler.java.compilers

import ru.DmN.pht.base.compiler.java.Compiler
import ru.DmN.pht.base.compiler.java.utils.CompilationContext
import ru.DmN.pht.base.parser.ast.Node
import ru.DmN.pht.base.utils.Variable

interface INodeCompiler<T : Node> {
    fun compile(node: T, compiler: Compiler, ctx: CompilationContext) {
        throw UnsupportedOperationException()
    }

    fun compileVal(node: T, compiler: Compiler, ctx: CompilationContext): Variable {
        throw UnsupportedOperationException()
    }
}