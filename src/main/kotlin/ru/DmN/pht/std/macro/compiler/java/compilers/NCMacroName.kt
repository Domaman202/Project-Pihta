package ru.DmN.pht.std.macro.compiler.java.compilers

import ru.DmN.pht.base.Compiler
import ru.DmN.pht.base.compiler.java.ctx.CompilationContext
import ru.DmN.pht.base.utils.Variable
import ru.DmN.pht.base.utils.VirtualType
import ru.DmN.pht.std.base.compiler.java.compilers.IStdNodeCompiler
import ru.DmN.pht.std.macro.ast.NodeMacroArg
import ru.DmN.pht.std.base.compiler.java.utils.ComputeType
import ru.DmN.pht.std.base.compiler.java.utils.computeName
import ru.DmN.pht.std.base.compiler.java.utils.method

object NCMacroName : IStdNodeCompiler<NodeMacroArg> {
    override fun calc(node: NodeMacroArg, compiler: Compiler, ctx: CompilationContext): VirtualType =
        VirtualType.STRING

    override fun compile(node: NodeMacroArg, compiler: Compiler, ctx: CompilationContext, ret: Boolean): Variable? =
        if (ret)
            ctx.method.node.run {
                visitLdcInsn(compute(node, compiler, ctx, ComputeType.NAME))
                Variable("pht$${node.hashCode()}", "java.lang.String", -1, true)
            }
        else null

    override fun compute(node: NodeMacroArg, compiler: Compiler, ctx: CompilationContext, type: ComputeType): Any =
        if (type == ComputeType.NAME)
            compiler.computeName(NCMacroArg.macro(node, ctx), ctx)
        else node
}