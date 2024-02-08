package ru.DmN.pht.jvm.compilers

import ru.DmN.siberia.Compiler
import ru.DmN.siberia.ast.Node
import ru.DmN.siberia.compiler.ctx.CompilationContext
import ru.DmN.siberia.compilers.INodeCompiler

interface IStdNodeCompiler<T : Node, R> : INodeCompiler<T> {
    fun getAsm(node: T, compiler: Compiler, ctx: CompilationContext): R
}