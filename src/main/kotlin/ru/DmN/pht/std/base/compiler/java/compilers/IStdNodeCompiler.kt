package ru.DmN.pht.std.base.compiler.java.compilers

import ru.DmN.pht.base.Compiler
import ru.DmN.pht.base.compiler.java.compilers.INodeCompiler
import ru.DmN.pht.base.compiler.java.ctx.CompilationContext
import ru.DmN.pht.base.parser.ast.Node
import ru.DmN.pht.std.base.compiler.java.utils.ComputeType

interface IStdNodeCompiler<T : Node> : INodeCompiler<T> {
    fun compute(node: T, compiler: Compiler, ctx: CompilationContext, type: ComputeType): Any? = node
    fun applyAnnotation(node: T, compiler: Compiler, ctx: CompilationContext, annotation: Node) = Unit
}