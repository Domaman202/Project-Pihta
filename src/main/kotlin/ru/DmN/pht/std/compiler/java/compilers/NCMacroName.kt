package ru.DmN.pht.std.compiler.java.compilers

import ru.DmN.pht.base.compiler.java.Compiler
import ru.DmN.pht.base.compiler.java.ctx.CompilationContext
import ru.DmN.pht.std.ast.NodeMacroArg
import ru.DmN.pht.std.compiler.java.utils.computeName
import ru.DmN.pht.std.compiler.java.utils.macro

object NCMacroName : IStdNodeCompiler<NodeMacroArg> {
    override fun compute(node: NodeMacroArg, compiler: Compiler, ctx: CompilationContext, name: Boolean): String =
        compiler.computeName(ctx.macro[node.name], ctx)
}