package ru.DmN.pht.std.compiler.java.compilers

import ru.DmN.pht.std.ast.NodeCompare
import ru.DmN.pht.std.compiler.java.utils.load
import ru.DmN.pht.std.compiler.java.utils.method
import ru.DmN.pht.std.processor.utils.nodeValue
import ru.DmN.siberia.Compiler
import ru.DmN.siberia.ast.Node
import ru.DmN.siberia.ast.NodeNodesList
import ru.DmN.siberia.compiler.ctx.CompilationContext
import ru.DmN.siberia.compilers.INodeCompiler
import ru.DmN.siberia.utils.Variable
import ru.DmN.siberia.utils.VirtualType
import ru.DmN.siberia.utils.line
import ru.DmN.siberia.utils.text

object NCIf : INodeCompiler<NodeNodesList> {
    override fun compile(node: NodeNodesList, compiler: Compiler, ctx: CompilationContext) =
        insertIf(node, { compiler.compile(it, ctx) }, compiler, ctx)

    override fun compileVal(node: NodeNodesList, compiler: Compiler, ctx: CompilationContext): Variable {
        var type: VirtualType? = null
        insertIf(node, { type = compiler.compileVal(it, ctx).apply { load(this, ctx.method.node) }.type }, compiler, ctx)
        return Variable.tmp(node, type)
    }

    private fun insertIf(node: NodeNodesList, compile: (Node) -> Unit, compiler: Compiler, ctx: CompilationContext) {
        val ifInsert = { compile(node.nodes[1]) }
        val elseInsert = { if (node.nodes.size == 3) compile(node.nodes[2]) }
        val cond = node.nodes[0]
        if (cond is NodeCompare) {
            NCCompare.insertIf(
                cond.text,
                cond.nodes,
                ifInsert,
                elseInsert,
                compiler, ctx
            )
        } else {
            NCCompare.insertIf(
                "!eq",
                mutableListOf(cond, nodeValue(node.line, true)),
                ifInsert,
                elseInsert,
                compiler, ctx
            )
        }
    }
}