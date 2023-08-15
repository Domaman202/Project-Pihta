package ru.DmN.pht.std.compiler.java.compilers

import ru.DmN.pht.base.compiler.java.Compiler
import ru.DmN.pht.base.compiler.java.compilers.NodeCompiler
import ru.DmN.pht.base.compiler.java.ctx.CompilationContext
import ru.DmN.pht.base.parser.ast.NodeNodesList
import ru.DmN.pht.base.utils.Variable
import ru.DmN.pht.base.utils.VirtualType
import ru.DmN.pht.std.ast.NodeMacroArg

object NCMacroArg : NodeCompiler<NodeMacroArg>() {
    override fun calcType(node: NodeMacroArg, compiler: Compiler, ctx: CompilationContext): VirtualType? =
        if (ctx.type.macro)
            compiler.calc(ctx.macro!![node.name], ctx)
        else null

    override fun compile(node: NodeMacroArg, compiler: Compiler, ctx: CompilationContext, ret: Boolean): Variable? =
        if (ctx.type.macro)
            compiler.compile(ctx.macro!![node.name], ctx, ret)
        else null
}