package ru.DmN.pht.std.compiler.java.compilers

import ru.DmN.pht.base.compiler.java.Compiler
import ru.DmN.pht.base.compiler.java.ctx.CompilationContext
import ru.DmN.pht.base.parser.ast.Node
import ru.DmN.pht.base.parser.ast.NodeNodesList
import ru.DmN.pht.base.utils.Variable
import ru.DmN.pht.std.compiler.java.utils.ComputeType
import ru.DmN.pht.std.compiler.java.utils.compute
import ru.DmN.pht.std.compiler.java.utils.computeName
import ru.DmN.pht.std.compiler.java.utils.global

object NCImport : IStdNodeCompiler<NodeNodesList> {
    override fun compile(node: NodeNodesList, compiler: Compiler, ctx: CompilationContext, ret: Boolean): Variable? {
        val gctx = ctx.global
        node.nodes.map { it -> compiler.compute<List<Node>>(it, ctx, ComputeType.NAME)
            .map { compiler.computeName(it, ctx) } }
            .forEach {
                val import = it[0]
                gctx.imports[it.getOrNull(1) ?: import.substring(import.lastIndexOf('.') + 1)] = import
            }
        return null
    }
}