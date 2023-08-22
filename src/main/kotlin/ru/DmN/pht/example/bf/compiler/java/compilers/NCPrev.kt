package ru.DmN.pht.example.bf.compiler.java.compilers

import ru.DmN.pht.base.compiler.java.Compiler
import ru.DmN.pht.base.compiler.java.compilers.INodeCompiler
import ru.DmN.pht.base.compiler.java.ctx.CompilationContext
import ru.DmN.pht.base.parser.ast.Node
import ru.DmN.pht.base.utils.Variable
import ru.DmN.pht.std.base.compiler.java.utils.method
import ru.DmN.pht.example.bf.compiler.java.utils.bf

object NCPrev : INodeCompiler<Node> {
    override fun compile(node: Node, compiler: Compiler, ctx: CompilationContext, ret: Boolean): Variable? {
        val bctx = ctx.bf
        ctx.method.node.run {
            bctx.lastIndex--
            visitIincInsn(bctx.index.id, -1)
        }
        return null
    }
}