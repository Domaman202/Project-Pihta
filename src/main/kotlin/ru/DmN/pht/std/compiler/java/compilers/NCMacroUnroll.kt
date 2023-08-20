package ru.DmN.pht.std.compiler.java.compilers

import ru.DmN.pht.base.compiler.java.Compiler
import ru.DmN.pht.base.compiler.java.ctx.CompilationContext
import ru.DmN.pht.base.parser.ast.Node
import ru.DmN.pht.base.parser.ast.NodeNodesList
import ru.DmN.pht.base.utils.Variable
import ru.DmN.pht.std.ast.NodeMacroArg
import ru.DmN.pht.std.compiler.java.utils.ComputeType
import ru.DmN.pht.std.compiler.java.utils.compute
import ru.DmN.pht.std.compiler.java.utils.computeName
import ru.DmN.pht.std.compiler.java.utils.sliceInsert

object NCMacroUnroll : StdSimpleNC<NodeNodesList>() {
    override fun compile(node: NodeNodesList, compiler: Compiler, ctx: CompilationContext, ret: Boolean): Variable? {
        val names = compiler.compute<List<Node>>(node.nodes.first(), ctx, ComputeType.NODE).map { "${compiler.computeName(it,ctx)}$" }
        node.nodes.drop(1).forEach { expr ->
            for (i in 0 until expr.nodes.size) {
                val it = expr.nodes[i]
                if (it is NodeMacroArg && names.any { name -> it.name.startsWith(name) }) { // todo: check tk oper
                    val list = expr.nodes as MutableList<Any?>
                    sliceInsert(list, i, compiler.compute(it, ctx, ComputeType.NODE))
                }
            }
        }
        return super.compile(node, compiler, ctx, ret)
    }
}