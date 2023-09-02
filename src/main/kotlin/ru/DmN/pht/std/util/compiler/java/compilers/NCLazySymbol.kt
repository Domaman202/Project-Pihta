package ru.DmN.pht.std.util.compiler.java.compilers

import ru.DmN.pht.base.compiler.java.Compiler
import ru.DmN.pht.base.compiler.java.ctx.CompilationContext
import ru.DmN.pht.base.utils.Variable
import ru.DmN.pht.base.utils.VirtualType
import ru.DmN.pht.std.base.compiler.java.compilers.IStdNodeCompiler
import ru.DmN.pht.std.base.compiler.java.utils.ComputeType
import ru.DmN.pht.std.base.compiler.java.utils.computeName
import ru.DmN.pht.std.base.compiler.java.utils.method
import ru.DmN.pht.std.util.ast.NodeLazySymbol

object NCLazySymbol : IStdNodeCompiler<NodeLazySymbol> {
    override fun calc(node: NodeLazySymbol, compiler: Compiler, ctx: CompilationContext): VirtualType =
        VirtualType.STRING

    override fun compile(node: NodeLazySymbol, compiler: Compiler, ctx: CompilationContext, ret: Boolean): Variable? =
        if (ret) {
            ctx.method.node.visitLdcInsn(process(node, compiler, ctx))
            Variable("pht$${node.hashCode()}", "java.lang.String", -1, true)
        } else null

    override fun compute(node: NodeLazySymbol, compiler: Compiler, ctx: CompilationContext, type: ComputeType): Any =
        if (type == ComputeType.NAME)
            process(node, compiler, ctx)
        else node

    private fun process(node: NodeLazySymbol, compiler: Compiler, ctx: CompilationContext): String {
        if (node.symbol == null)
            node.symbol = node.nodes.map { compiler.computeName(it, ctx) }.reduce { acc, s -> acc + s }
        return node.symbol!!
    }
}