package ru.DmN.pht.std.compiler.java.compilers

import ru.DmN.pht.base.compiler.java.Compiler
import ru.DmN.pht.base.compiler.java.compilers.NodeCompiler
import ru.DmN.pht.base.compiler.java.ctx.CompilationContext
import ru.DmN.pht.base.parser.ast.Node
import ru.DmN.pht.base.parser.ast.NodeNodesList
import ru.DmN.pht.base.utils.Variable
import ru.DmN.pht.base.utils.VirtualType
import ru.DmN.pht.std.ast.NodeMacroArg

object NCMacroArg : NodeCompiler<NodeNodesList>() {
    override fun calc(node: NodeNodesList, compiler: Compiler, ctx: CompilationContext): VirtualType? =
        if (ctx.type.macro)
            compiler.calc(macro(node, compiler, ctx), ctx)
        else null

    override fun compile(node: NodeNodesList, compiler: Compiler, ctx: CompilationContext, ret: Boolean): Variable? =
        if (ctx.type.macro)
            compiler.compile(macro(node, compiler, ctx), ctx, ret)
        else null

    override fun compute(node: NodeNodesList, compiler: Compiler, ctx: CompilationContext, name: Boolean): Any? =
        if (ctx.type.macro)
            compiler.compute<Any?>(macro(node, compiler, ctx), ctx, name)
        else null

    override fun applyAnnotation(node: NodeNodesList, compiler: Compiler, ctx: CompilationContext, annotation: Node) {
        if (ctx.type.macro) {
            compiler.applyAnnotation(macro(node, compiler, ctx), ctx, annotation)
        }
    }

    private fun macro(node: NodeNodesList, compiler: Compiler, ctx: CompilationContext) =
        ctx.macro!![compiler.computeStringConst(node.nodes.first(), ctx)]
}