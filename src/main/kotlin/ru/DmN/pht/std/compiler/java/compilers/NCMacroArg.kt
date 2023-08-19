package ru.DmN.pht.std.compiler.java.compilers

import ru.DmN.pht.base.compiler.java.Compiler
import ru.DmN.pht.base.compiler.java.ctx.CompilationContext
import ru.DmN.pht.base.parser.ast.Node
import ru.DmN.pht.base.parser.ast.NodeNodesList
import ru.DmN.pht.base.utils.Variable
import ru.DmN.pht.base.utils.VirtualType
import ru.DmN.pht.std.compiler.java.*

object NCMacroArg : IStdNodeCompiler<NodeNodesList> {
    override fun calc(node: NodeNodesList, compiler: Compiler, ctx: CompilationContext): VirtualType? =
        if (ctx.isMacro())
            compiler.calc(macro(node, compiler, ctx), ctx)
        else null

    override fun compile(node: NodeNodesList, compiler: Compiler, ctx: CompilationContext, ret: Boolean): Variable? =
        if (ctx.isMacro())
            compiler.compile(macro(node, compiler, ctx), ctx, ret)
        else null

    override fun compute(node: NodeNodesList, compiler: Compiler, ctx: CompilationContext, name: Boolean): Any? =
        if (ctx.isMacro())
            compiler.compute<Any?>(macro(node, compiler, ctx), ctx, name)
        else null

    override fun applyAnnotation(node: NodeNodesList, compiler: Compiler, ctx: CompilationContext, annotation: Node) {
        if (ctx.isMacro()) {
            compiler.applyAnnotation(macro(node, compiler, ctx), ctx, annotation)
        }
    }

    private fun macro(node: NodeNodesList, compiler: Compiler, ctx: CompilationContext) =
        ctx.macro[compiler.computeName(node.nodes.first(), ctx)]
}