package ru.DmN.pht.std.compiler.java.compilers

import ru.DmN.pht.base.compiler.java.Compiler
import ru.DmN.pht.base.compiler.java.compilers.NodeCompiler
import ru.DmN.pht.base.compiler.java.ctx.CompilationContext
import ru.DmN.pht.base.utils.Variable
import ru.DmN.pht.base.utils.VirtualType
import ru.DmN.pht.base.parser.ast.NodeNodesList
import ru.DmN.pht.std.compiler.java.ctx.global
import ru.DmN.pht.std.compiler.java.ctx.isMethod
import ru.DmN.pht.std.compiler.java.ctx.method
import ru.DmN.pht.std.utils.loadCast

object NCAs : NodeCompiler<NodeNodesList>() {
    override fun calc(node: NodeNodesList, compiler: Compiler, ctx: CompilationContext): VirtualType? =
        if (ctx.isMethod())
            ctx.global.getType(compiler, node.nodes.first().getConstValueAsString())
        else null

    override fun compile(node: NodeNodesList, compiler: Compiler, ctx: CompilationContext, ret: Boolean): Variable? =
        if (ret) {
            val type = calc(node, compiler, ctx)!!
            loadCast(compiler.compile(node.nodes.last(), ctx, true)!!, type, ctx.method.node)
            Variable("pht$${node.hashCode()}", type.name, -1, true)
        } else null
}