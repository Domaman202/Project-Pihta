package ru.DmN.pht.std.compilers

import ru.DmN.pht.base.ast.Node
import ru.DmN.pht.base.ast.NodeNodesList
import ru.DmN.pht.base.compiler.java.Compiler
import ru.DmN.pht.base.compiler.java.compilers.INodeCompiler
import ru.DmN.pht.base.compiler.java.utils.CompilationContext
import ru.DmN.pht.base.utils.Variable
import ru.DmN.pht.base.utils.VirtualType
import ru.DmN.pht.std.ast.NodeEquals
import ru.DmN.pht.std.processor.utils.nodeValueOf
import ru.DmN.pht.std.utils.line

object NCIf : INodeCompiler<NodeNodesList> {
    override fun compile(node: NodeNodesList, compiler: Compiler, ctx: CompilationContext) =
        insertIf(node, { compiler.compile(it, ctx) }, compiler, ctx)

    override fun compileVal(node: NodeNodesList, compiler: Compiler, ctx: CompilationContext): Variable {
        var type: VirtualType? = null
        insertIf(node, { type = compiler.compileVal(node, ctx).type }, compiler, ctx)
        return Variable.tmp(node, type)
    }

    private fun insertIf(node: NodeNodesList, compile: (Node) -> Unit, compiler: Compiler, ctx: CompilationContext) {
        val cond = node.nodes[0]
        if (cond.token.text == "!=")
            println()
        if (cond is NodeEquals) {
            NCCompare.insertIf(
                cond.token.text!!,
                cond.nodes,
                { compile(node.nodes[1]) },
                { if (node.nodes.size == 3) compile(node.nodes[2]) },
                compiler, ctx
            )
        } else {
            NCCompare.insertIf(
                "=",
                mutableListOf(cond, nodeValueOf(node.line, true)),
                { compile(node.nodes[1]) },
                { if (node.nodes.size == 3) compile(node.nodes[2]) },
                compiler, ctx
            )
        }
    }
}