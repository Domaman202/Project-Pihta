package ru.DmN.pht.std.compiler.java.compilers

import ru.DmN.pht.base.compiler.java.Compiler
import ru.DmN.pht.base.compiler.java.ctx.CompilationContext
import ru.DmN.pht.base.parser.ast.Node
import ru.DmN.pht.base.parser.ast.NodeNodesList
import ru.DmN.pht.base.utils.Variable
import ru.DmN.pht.base.utils.VirtualType
import ru.DmN.pht.std.ast.NodeMacro
import ru.DmN.pht.std.compiler.java.ctx.MacroContext
import ru.DmN.pht.std.compiler.java.global

object NCMacroB : IStdNodeCompiler<NodeMacro> {
    override fun calc(node: NodeMacro, compiler: Compiler, ctx: CompilationContext): VirtualType? {
        val result = process(node, ctx)
        return NCDefault.calc(result.first, compiler, result.second)
    }

    override fun compile(node: NodeMacro, compiler: Compiler, ctx: CompilationContext, ret: Boolean): Variable? {
        val result = process(node, ctx)
        return NCDefault.compile(result.first, compiler, result.second, ret)
    }

    override fun applyAnnotation(node: NodeMacro, compiler: Compiler, ctx: CompilationContext, annotation: Node) {
        val result = process(node, ctx)
        return NCDefault.applyAnnotation(result.first, compiler, result.second, annotation)
    }

    private fun process(node: NodeMacro, ctx: CompilationContext): Pair<NodeNodesList, CompilationContext> {
        val macro = ctx.global.macros.find { it.name == node.name }!!
        val mctx = MacroContext()
        macro.args.forEachIndexed { i, it -> mctx.args[it] = node.nodes[i] }
        return NCMacroA.process(macro, ctx, mctx)
    }
}