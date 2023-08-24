package ru.DmN.pht.std.macro.compiler.java.compilers

import ru.DmN.pht.base.compiler.java.Compiler
import ru.DmN.pht.base.compiler.java.ctx.CompilationContext
import ru.DmN.pht.base.utils.Variable
import ru.DmN.pht.base.utils.VirtualType
import ru.DmN.pht.std.base.compiler.java.compilers.IStdNodeCompiler
import ru.DmN.pht.std.macro.ast.NodeMacroArg
import ru.DmN.pht.std.base.compiler.java.utils.ComputeType
import ru.DmN.pht.std.base.compiler.java.utils.compute
import ru.DmN.pht.std.base.compiler.java.utils.method

object NCMacroArgCount : IStdNodeCompiler<NodeMacroArg> {
    override fun calc(node: NodeMacroArg, compiler: Compiler, ctx: CompilationContext): VirtualType =
        VirtualType.INT

    override fun compile(node: NodeMacroArg, compiler: Compiler, ctx: CompilationContext, ret: Boolean): Variable? =
        if (ret)
            ctx.method.node.run {
                visitLdcInsn(compute(node, compiler, ctx, ComputeType.NUMBER))
                Variable("pht$${node.hashCode()}", "int", -1, true)
            }
        else null

    override fun compute(node: NodeMacroArg, compiler: Compiler, ctx: CompilationContext, type: ComputeType): Any =
        when (type) {
            ComputeType.NUMBER -> compute(node, compiler, ctx)
            ComputeType.NAME -> compute(node, compiler, ctx).toString()
            else -> node
        }

    private fun compute(node: NodeMacroArg, compiler: Compiler, ctx: CompilationContext) =
        compiler.compute<Any?>(NCMacroArg.macro(node, ctx), ctx, ComputeType.NODE).let { if (it is List<*>) it.size else 1 }
}