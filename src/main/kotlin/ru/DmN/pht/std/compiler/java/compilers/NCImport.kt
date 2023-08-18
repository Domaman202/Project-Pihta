package ru.DmN.pht.std.compiler.java.compilers

import ru.DmN.pht.base.compiler.java.Compiler
import ru.DmN.pht.base.compiler.java.compilers.NodeCompiler
import ru.DmN.pht.base.compiler.java.ctx.CompilationContext
import ru.DmN.pht.base.parser.ast.Node
import ru.DmN.pht.base.parser.ast.NodeNodesList
import ru.DmN.pht.base.utils.Variable

object NCImport : NodeCompiler<NodeNodesList>() {
    override fun compile(node: NodeNodesList, compiler: Compiler, ctx: CompilationContext, ret: Boolean): Variable? {
        node.nodes.map { it -> compiler.compute<List<Node>>(it, ctx, true).map { compiler.computeStringConst(it, ctx) } }
            .forEach {
                val import = it[0]
                ctx.global.imports[it.getOrNull(1) ?: import.substring(import.lastIndexOf('.') + 1)] = import
            }
        return null
    }
}