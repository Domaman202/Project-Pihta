package ru.DmN.pht.std.compiler.java.compilers

import ru.DmN.pht.base.compiler.java.Compiler
import ru.DmN.pht.base.compiler.java.compilers.NodeCompiler
import ru.DmN.pht.base.compiler.java.ctx.CompilationContext
import ru.DmN.pht.base.utils.Variable
import ru.DmN.pht.base.utils.VirtualType
import ru.DmN.pht.base.parser.ast.NodeNodesList
import ru.DmN.pht.std.utils.loadCast

object NCCast : NodeCompiler<NodeNodesList>() {
    override fun calcType(node: NodeNodesList, compiler: Compiler, ctx: CompilationContext): VirtualType =
        ctx.gctx.getType(compiler, node.nodes.first().getConstValueAsString())

    override fun compile(node: NodeNodesList, compiler: Compiler, ctx: CompilationContext, ret: Boolean): Variable? {
        return if (ret && ctx.type.method) {
            val type = calcType(node, compiler, ctx)
            loadCast(compiler.compile(node.nodes.last(), ctx, true)!!, type, ctx.mctx!!.node)
            Variable("pht$${node.hashCode()}", type.name, -1, true)
        } else null
    }
}