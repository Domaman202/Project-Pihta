package ru.DmN.pht.std.compiler.java.compilers

import ru.DmN.siberia.ast.Node
import ru.DmN.siberia.compiler.java.Compiler
import ru.DmN.siberia.compiler.java.compilers.INodeCompiler
import ru.DmN.siberia.compiler.java.utils.CompilationContext

object NCSkip : INodeCompiler<Node> {
    override fun compile(node: Node, compiler: Compiler, ctx: CompilationContext) =
        Unit
}