package ru.DmN.pht.cpp.compilers

import ru.DmN.pht.cpp.compiler.utils.load
import ru.DmN.siberia.ast.NodeNodesList
import ru.DmN.siberia.compiler.Compiler
import ru.DmN.siberia.compiler.ctx.CompilationContext

object NCCycle : ICppCompiler<NodeNodesList> {
    override fun StringBuilder.compile(node: NodeNodesList, compiler: Compiler, ctx: CompilationContext) {
        append("while (")
        compiler.compileVal(node.nodes[0], ctx).load(this)
        append(") {\n")
        node.nodes.stream().skip(1).forEach {
            compiler.compile(it, ctx)
            append(";\n")
        }
        append('}')
    }
}