package ru.DmN.pht.std.compiler.java.compilers

import ru.DmN.pht.base.compiler.java.Compiler
import ru.DmN.pht.base.compiler.java.compilers.NodeCompiler
import ru.DmN.pht.base.compiler.java.ctx.CompilationContext
import ru.DmN.pht.base.utils.Variable
import ru.DmN.pht.base.parser.ast.NodeNodesList
import ru.DmN.pht.std.utils.insertRet

object NCReturn : NodeCompiler<NodeNodesList>() {
    override fun compile(node: NodeNodesList, compiler: Compiler, ctx: CompilationContext, ret: Boolean): Variable? {
        if (ctx.type.method) {
            val mctx = ctx.mctx!!
            insertRet(compiler.compile(node.nodes.first(), ctx, true), ctx.gctx.getType(compiler, mctx.method.rettype.type), mctx.node)
        }
        return null
    }
}