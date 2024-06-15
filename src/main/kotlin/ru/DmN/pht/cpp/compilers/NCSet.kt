package ru.DmN.pht.cpp.compilers

import ru.DmN.pht.ast.NodeSet
import ru.DmN.pht.cpp.compiler.utils.load
import ru.DmN.siberia.compiler.Compiler
import ru.DmN.siberia.compiler.ctx.CompilationContext

object NCSet : ICppCompiler<NodeSet> {
    override fun StringBuilder.compile(node: NodeSet, compiler: Compiler, ctx: CompilationContext) {
        append(node.name).append(" = ")
        compiler.compileVal(node.nodes[0], ctx).load(this)
    }
}