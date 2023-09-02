package ru.DmN.pht.std.util.compiler.java.compilers

import ru.DmN.pht.base.compiler.java.Compiler
import ru.DmN.pht.base.compiler.java.compilers.NCDefault
import ru.DmN.pht.base.compiler.java.ctx.CompilationContext
import ru.DmN.pht.base.lexer.Token
import ru.DmN.pht.base.parser.ast.Node
import ru.DmN.pht.base.parser.ast.NodeNodesList
import ru.DmN.pht.base.utils.Variable
import ru.DmN.pht.base.utils.VirtualType
import ru.DmN.pht.std.base.compiler.java.compilers.IStdNodeCompiler
import ru.DmN.pht.std.base.compiler.java.utils.ComputeType
import ru.DmN.pht.std.base.compiler.java.utils.computeInt

object NCRepeatExpr : IStdNodeCompiler<NodeNodesList> {
    override fun calc(node: NodeNodesList, compiler: Compiler, ctx: CompilationContext): VirtualType? =
        compiler.calc(node.nodes.first(), ctx)

    override fun compile(node: NodeNodesList, compiler: Compiler, ctx: CompilationContext, ret: Boolean): Variable? =
        NCDefault.compile(gen(node, compiler, ctx), compiler, ctx, ret)

    override fun compute(node: NodeNodesList, compiler: Compiler, ctx: CompilationContext, type: ComputeType): Any? =
        gen(node, compiler, ctx)

    private fun gen(node: NodeNodesList, compiler: Compiler, ctx: CompilationContext): NodeNodesList {
        val arr = ArrayList<Node>()
        val n = node.nodes.first()
        for (j in 0 until compiler.computeInt(node.nodes.last(), ctx))
            arr += n
        return NodeNodesList(Token(node.tkOperation.line, Token.Type.OPERATION, "valn"), arr)
    }
}