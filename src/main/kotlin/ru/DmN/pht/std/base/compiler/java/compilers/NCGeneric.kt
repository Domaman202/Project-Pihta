package ru.DmN.pht.std.base.compiler.java.compilers

import ru.DmN.pht.base.Compiler
import ru.DmN.pht.base.compiler.java.ctx.CompilationContext
import ru.DmN.pht.base.parser.ast.Node
import ru.DmN.pht.base.parser.ast.NodeNodesList
import ru.DmN.pht.base.utils.Variable
import ru.DmN.pht.std.base.ast.IGenericsContainer
import ru.DmN.pht.std.base.compiler.java.utils.ComputeType
import ru.DmN.pht.std.base.compiler.java.utils.applyAnnotation
import ru.DmN.pht.std.base.compiler.java.utils.compute

object NCGeneric : StdSimpleNC<NodeNodesList>() {
    override fun compile(node: NodeNodesList, compiler: Compiler, ctx: CompilationContext, ret: Boolean): Variable? {
        val args = node.nodes.take(2).map { compiler.compute<String>(it, ctx, ComputeType.NAME) }
        val nodes = node.nodes.drop(2)
        nodes.forEach {
            if (it is IGenericsContainer) {
                (it.attributes.getOrPut("generics") { ArrayList<Pair<String, String>>() } as MutableList<Pair<String, String>>) += Pair(args.first(), args.last())
            }
        }
        nodes.dropLast(1).forEach { compiler.compile(it, ctx, false) }
        return compiler.compile(nodes.last(), ctx, ret)
    }

    override fun applyAnnotation(node: NodeNodesList, compiler: Compiler, ctx: CompilationContext, annotation: Node) =
        node.nodes.drop(2).forEach { compiler.applyAnnotation(it, ctx, annotation) }
}