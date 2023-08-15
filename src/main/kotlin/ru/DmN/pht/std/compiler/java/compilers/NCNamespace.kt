package ru.DmN.pht.std.compiler.java.compilers

import ru.DmN.pht.base.compiler.java.Compiler
import ru.DmN.pht.base.compiler.java.compilers.NodeCompiler
import ru.DmN.pht.base.compiler.java.ctx.CompilationContext
import ru.DmN.pht.base.utils.Variable
import ru.DmN.pht.std.ast.NodeNamespace

object NCNamespace : NodeCompiler<NodeNamespace>() {
    override fun compile(node: NodeNamespace, compiler: Compiler, ctx: CompilationContext, ret: Boolean): Variable? {
        val context = ctx.with(ctx.global.with(ctx.global.name(node.name)))
        node.nodes.forEach { compiler.compile(it, context, ret) }
        return null
    }
}