package ru.DmN.pht.std.compiler.java.compilers

import ru.DmN.pht.base.compiler.java.Compiler
import ru.DmN.pht.base.compiler.java.compilers.NCNodesList
import ru.DmN.pht.base.compiler.java.compilers.NodeCompiler
import ru.DmN.pht.base.compiler.java.ctx.CompilationContext
import ru.DmN.pht.base.compiler.java.ctx.MacroContext
import ru.DmN.pht.base.utils.Variable
import ru.DmN.pht.base.utils.VirtualType
import ru.DmN.pht.std.ast.NodeMacro

object NCMacro : NodeCompiler<NodeMacro>() {
    override fun calcType(node: NodeMacro, compiler: Compiler, ctx: CompilationContext): VirtualType? =
        NCNodesList.calcType(ctx.global.macros.find { it.name == node.name }!!, compiler, ctx)

    override fun compile(node: NodeMacro, compiler: Compiler, ctx: CompilationContext, ret: Boolean): Variable? {
        val macro = ctx.global.macros.find { it.name == node.name }!!
        val mctx = MacroContext()
        macro.args.forEachIndexed { i, it -> mctx.args[it] = node.nodes[i] }
        return NCNodesList.compile(macro, compiler, ctx.with(ctx.type.with(CompilationContext.Type.MACRO)).with(mctx), ret)
    }
}