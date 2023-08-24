package ru.DmN.pht.std.base.compiler.java.compilers

import ru.DmN.pht.base.compiler.java.Compiler
import ru.DmN.pht.base.compiler.java.ctx.CompilationContext
import ru.DmN.pht.base.parser.ast.NodeNodesList
import ru.DmN.pht.base.utils.Variable
import ru.DmN.pht.base.utils.VirtualType
import ru.DmN.pht.std.base.compiler.java.utils.computeName
import ru.DmN.pht.std.base.compiler.java.utils.global
import ru.DmN.pht.std.base.compiler.java.utils.method
import ru.DmN.pht.std.base.utils.loadCast

object NCAs : IStdNodeCompiler<NodeNodesList> {
    override fun calc(node: NodeNodesList, compiler: Compiler, ctx: CompilationContext): VirtualType =
        ctx.global.getType(compiler, compiler.computeName(node.nodes.first(), ctx))

    override fun compile(node: NodeNodesList, compiler: Compiler, ctx: CompilationContext, ret: Boolean): Variable? =
        if (ret) {
            val type = calc(node, compiler, ctx)
            loadCast(compiler.compile(node.nodes.last(), ctx, true)!!, type, ctx.method.node)
            Variable("pht$${node.hashCode()}", type.name, -1, true)
        } else null
}