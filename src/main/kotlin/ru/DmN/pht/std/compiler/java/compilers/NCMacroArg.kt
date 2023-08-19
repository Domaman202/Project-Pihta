package ru.DmN.pht.std.compiler.java.compilers

import ru.DmN.pht.base.compiler.java.Compiler
import ru.DmN.pht.base.compiler.java.ctx.CompilationContext
import ru.DmN.pht.base.parser.ast.Node
import ru.DmN.pht.base.utils.Variable
import ru.DmN.pht.base.utils.VirtualType
import ru.DmN.pht.std.ast.NodeMacroArg
import ru.DmN.pht.std.compiler.java.utils.applyAnnotation
import ru.DmN.pht.std.compiler.java.utils.compute
import ru.DmN.pht.std.compiler.java.utils.isMacro
import ru.DmN.pht.std.compiler.java.utils.macro

object NCMacroArg : IStdNodeCompiler<NodeMacroArg> {
    override fun calc(node: NodeMacroArg, compiler: Compiler, ctx: CompilationContext): VirtualType? =
        if (ctx.isMacro())
            compiler.calc(macro(node, ctx), ctx)
        else null

    override fun compile(node: NodeMacroArg, compiler: Compiler, ctx: CompilationContext, ret: Boolean): Variable? =
        if (ctx.isMacro())
            compiler.compile(macro(node, ctx), ctx, ret)
        else null

    override fun compute(node: NodeMacroArg, compiler: Compiler, ctx: CompilationContext, name: Boolean): Any? =
        if (ctx.isMacro())
            compiler.compute<Any?>(macro(node, ctx), ctx, name)
        else null

    override fun applyAnnotation(node: NodeMacroArg, compiler: Compiler, ctx: CompilationContext, annotation: Node) {
        if (ctx.isMacro()) {
            compiler.applyAnnotation(macro(node, ctx), ctx, annotation)
        }
    }

    private fun macro(node: NodeMacroArg, ctx: CompilationContext): Node =
        ctx.macro[node.name]
}