package ru.DmN.pht.std.base.compiler.java.compilers

import ru.DmN.pht.base.Compiler
import ru.DmN.pht.base.compiler.java.ctx.CompilationContext
import ru.DmN.pht.base.parser.ast.NodeNodesList
import ru.DmN.pht.base.utils.Variable
import ru.DmN.pht.std.base.compiler.java.utils.global
import ru.DmN.pht.std.base.compiler.java.utils.method
import ru.DmN.pht.std.base.utils.insertRet

object NCReturn : IStdNodeCompiler<NodeNodesList> {
    override fun compile(node: NodeNodesList, compiler: Compiler, ctx: CompilationContext, ret: Boolean): Variable? {
        val mctx = ctx.method
        insertRet(
            compiler.compile(node.nodes.first(), ctx, true),
            ctx.global.getType(compiler, mctx.method.rettype.type),
            mctx.node
        )
        return null
    }
}