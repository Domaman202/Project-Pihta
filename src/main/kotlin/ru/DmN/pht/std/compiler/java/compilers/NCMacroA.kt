package ru.DmN.pht.std.compiler.java.compilers

import ru.DmN.pht.base.compiler.java.Compiler
import ru.DmN.pht.base.compiler.java.ctx.CompilationContext
import ru.DmN.pht.std.compiler.java.ctx.MacroContext
import ru.DmN.pht.base.compiler.java.utils.MacroDefine
import ru.DmN.pht.base.parser.ast.Node
import ru.DmN.pht.base.parser.ast.NodeNodesList
import ru.DmN.pht.base.utils.Variable
import ru.DmN.pht.base.utils.VirtualType
import ru.DmN.pht.std.compiler.java.computeName
import ru.DmN.pht.std.compiler.java.global
import ru.DmN.pht.std.compiler.java.with

object NCMacroA : IStdNodeCompiler<NodeNodesList> {
    override fun calc(node: NodeNodesList, compiler: Compiler, ctx: CompilationContext): VirtualType? {
        val result = process(node, compiler, ctx)
        return NCDefault.calc(result.first, compiler, result.second)
    }

    override fun compile(node: NodeNodesList, compiler: Compiler, ctx: CompilationContext, ret: Boolean): Variable? {
        val result = process(node, compiler, ctx)
        return NCDefault.compile(result.first, compiler, result.second, ret)
    }

    override fun applyAnnotation(node: NodeNodesList, compiler: Compiler, ctx: CompilationContext, annotation: Node) {
        val result = process(node, compiler, ctx)
        return NCDefault.applyAnnotation(result.first, compiler, result.second, annotation)
    }

    private fun process(node: NodeNodesList, compiler: Compiler, ctx: CompilationContext): Pair<NodeNodesList, CompilationContext> {
        val name = compiler.computeName(node.nodes.first(), ctx)
        val macro = ctx.global.macros.find { it.name == name }!!
        val mctx = MacroContext()
        macro.args.forEachIndexed { i, it -> mctx.args[it] = node.nodes[i + 1] }
        return process(macro, ctx, mctx)
    }

    fun process(macro: MacroDefine, ctx: CompilationContext, mctx: MacroContext) =
        Pair(macro.toNodesList(), ctx.with(macro.ctx.combineWith(ctx.global)).with(mctx))
}