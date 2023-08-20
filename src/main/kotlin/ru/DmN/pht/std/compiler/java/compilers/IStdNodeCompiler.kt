package ru.DmN.pht.std.compiler.java.compilers

import ru.DmN.pht.base.compiler.java.Compiler
import ru.DmN.pht.base.compiler.java.compilers.INodeCompiler
import ru.DmN.pht.base.compiler.java.ctx.CompilationContext
import ru.DmN.pht.base.parser.ast.Node

interface IStdNodeCompiler<T : Node> : INodeCompiler<T> {
    fun compute(node: T, compiler: Compiler, ctx: CompilationContext, name: Boolean): Any? = node
    fun applyAnnotation(node: T, compiler: Compiler, ctx: CompilationContext, annotation: Node) = Unit
}