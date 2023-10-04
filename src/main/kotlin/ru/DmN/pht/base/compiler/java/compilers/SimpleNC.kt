package ru.DmN.pht.base.compiler.java.compilers

import ru.DmN.pht.base.compiler.java.Compiler
import ru.DmN.pht.base.compiler.java.utils.CompilationContext
import ru.DmN.pht.base.parser.ast.NodeNodesList
import ru.DmN.pht.base.utils.Variable

open class SimpleNC<T : NodeNodesList> : INodeCompiler<T> {
    override fun compile(node: T, compiler: Compiler, ctx: CompilationContext) =
        node.nodes.forEach { compiler.compile(it, ctx) }

    override fun compileVal(node: T, compiler: Compiler, ctx: CompilationContext): Variable {
        node.nodes.dropLast(1).forEach { compiler.compile(it, ctx) }
        return compiler.compileVal(node.nodes.last(), ctx)
    }
}