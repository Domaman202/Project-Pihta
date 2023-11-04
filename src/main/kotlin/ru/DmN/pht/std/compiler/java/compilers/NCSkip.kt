package ru.DmN.pht.std.compiler.java.compilers

import ru.DmN.pht.base.ast.Node
import ru.DmN.pht.base.compiler.java.Compiler
import ru.DmN.pht.base.compiler.java.compilers.INodeCompiler
import ru.DmN.pht.base.compiler.java.utils.CompilationContext

object NCSkip : INodeCompiler<Node> {
    override fun compile(node: Node, compiler: Compiler, ctx: CompilationContext) =
        Unit
}