package ru.DmN.pht.jvm.compilers

import ru.DmN.pht.compiler.java.utils.computeValue
import ru.DmN.siberia.Compiler
import ru.DmN.siberia.ast.NodeNodesList
import ru.DmN.siberia.compiler.ctx.CompilationContext

object NCList : IStdNodeCompiler<NodeNodesList, Nothing, Array<*>> {
    override fun computeValue(node: NodeNodesList, compiler: Compiler, ctx: CompilationContext): Array<*> =
        node.nodes.stream().map { compiler.computeValue(it, ctx) }.toArray()
}