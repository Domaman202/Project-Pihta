package ru.DmN.pht.std.compiler.java.compilers

import ru.DmN.pht.base.compiler.java.Compiler
import ru.DmN.pht.base.compiler.java.compilers.NodeCompiler
import ru.DmN.pht.base.compiler.java.ctx.CompilationContext
import ru.DmN.pht.base.parser.ast.NodeNodesList

object NCMacroName : NodeCompiler<NodeNodesList>() {
    override fun compute(node: NodeNodesList, compiler: Compiler, ctx: CompilationContext, name: Boolean): String =
        compiler.computeStringConst(ctx.macro!![compiler.computeStringConst(node.nodes.first(), ctx)], ctx)
}