package ru.DmN.pht.std.base.compiler.java.compilers

import ru.DmN.pht.base.compiler.java.Compiler
import ru.DmN.pht.base.compiler.java.ctx.CompilationContext
import ru.DmN.pht.base.parser.ast.Node
import ru.DmN.pht.base.parser.ast.NodeNodesList
import ru.DmN.pht.base.utils.Variable
import ru.DmN.pht.std.base.ast.NodeMacroArg
import ru.DmN.pht.std.base.ast.NodeMacroUnroll
import ru.DmN.pht.std.base.compiler.java.utils.*

object NCMacroUnroll : IStdNodeCompiler<NodeMacroUnroll> {
    override fun compile(node: NodeMacroUnroll, compiler: Compiler, ctx: CompilationContext, ret: Boolean): Variable? {
        val mctx = ctx.macro
        val expr = compiler.compute<Node>(node.nodes.last(), ctx, ComputeType.NODE)
        val pair = compiler.compute<List<Node>>(node.nodes.first(), ctx, ComputeType.NODE).map { compiler.computeName(it, ctx) }
        val name = "${pair.first()}$${node.macro}"
        compiler.compute<Any?>(mctx["${pair.last()}$${node.macro}"], ctx, ComputeType.NODE)
            .let { if (it is Node) listOf(it) else it as List<Node> }
            .forEach { compiler.compile(expr, ctx.with(mctx.with(name, it)), false) }
        return null
    }
}