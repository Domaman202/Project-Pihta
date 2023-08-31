package ru.DmN.pht.std.macro.compiler.java.compilers

import ru.DmN.pht.base.Compiler
import ru.DmN.pht.base.compiler.java.ctx.CompilationContext
import ru.DmN.pht.base.parser.ast.Node
import ru.DmN.pht.base.utils.Variable
import ru.DmN.pht.base.utils.VirtualType
import ru.DmN.pht.std.base.compiler.java.compilers.IStdNodeCompiler
import ru.DmN.pht.std.macro.ast.NodeMacroArg
import ru.DmN.pht.std.base.compiler.java.utils.*

object NCMacroArg : IStdNodeCompiler<NodeMacroArg> {
    override fun calc(node: NodeMacroArg, compiler: Compiler, ctx: CompilationContext): VirtualType? =
        compiler.calc(macro(node, ctx), ctx)

    override fun compile(node: NodeMacroArg, compiler: Compiler, ctx: CompilationContext, ret: Boolean): Variable? =
        compiler.compile(macro(node, ctx), ctx, ret)

    override fun compute(node: NodeMacroArg, compiler: Compiler, ctx: CompilationContext, type: ComputeType): Any? =
        compiler.compute(macro(node, ctx), ctx, type)

    override fun applyAnnotation(node: NodeMacroArg, compiler: Compiler, ctx: CompilationContext, annotation: Node) =
        compiler.applyAnnotation(macro(node, ctx), ctx, annotation)

    fun macro(node: NodeMacroArg, ctx: CompilationContext): Node =
        ctx.macro[node.name]
}