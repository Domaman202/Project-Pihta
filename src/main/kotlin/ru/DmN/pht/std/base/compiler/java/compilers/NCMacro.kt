package ru.DmN.pht.std.base.compiler.java.compilers

import ru.DmN.pht.base.compiler.java.Compiler
import ru.DmN.pht.base.compiler.java.ctx.CompilationContext
import ru.DmN.pht.base.lexer.Token
import ru.DmN.pht.base.parser.ast.Node
import ru.DmN.pht.base.parser.ast.NodeNodesList
import ru.DmN.pht.base.utils.Variable
import ru.DmN.pht.base.utils.VirtualType
import ru.DmN.pht.std.base.ast.NodeMacro
import ru.DmN.pht.std.base.compiler.java.ctx.MacroContext
import ru.DmN.pht.std.base.compiler.java.utils.global
import ru.DmN.pht.std.base.compiler.java.utils.isMacro
import ru.DmN.pht.std.base.compiler.java.utils.macro
import ru.DmN.pht.std.base.compiler.java.utils.with

object NCMacro : IStdNodeCompiler<NodeMacro> {
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
        val mctx = ctxOf(ctx)
        if (macro.args.size == node.nodes.size)
            macro.args.forEachIndexed { i, it -> mctx.args[it] = node.nodes[i] }
        else if (macro.args.isNotEmpty()) {
            macro.args.dropLast(1).forEachIndexed { i, it -> mctx.args[it] = node.nodes[i] }
            mctx.args[macro.args.last()] = NodeNodesList(
                Token(node.tkOperation.line, Token.Type.OPERATION, "valn"),
                node.nodes.drop(macro.args.size - 1).toMutableList()
            )
        }
        return Pair(macro.toNodesList(), ctx.with(macro.ctx.combineWith(ctx.global)).with(mctx))
    }

    fun ctxOf(ctx: CompilationContext): MacroContext {
        return if (ctx.isMacro())
            MacroContext(ctx.macro.args)
        else MacroContext()
    }
}