package ru.DmN.pht.cpp.compilers

import ru.DmN.pht.cpp.compiler.utils.load
import ru.DmN.siberia.ast.NodeNodesList
import ru.DmN.siberia.compiler.Compiler
import ru.DmN.siberia.compiler.ctx.CompilationContext
import ru.DmN.siberia.utils.Variable

object NCIf : ICppCompiler<NodeNodesList> {
    override fun StringBuilder.compile(node: NodeNodesList, compiler: Compiler, ctx: CompilationContext) {
        append("if (")
        compiler.compileVal(node.nodes[0], ctx)
        append(")\n")
        compiler.compile(node.nodes[1], ctx)
        if (node.nodes.size == 3) {
            append(";\nelse\n")
            compiler.compile(node.nodes[2], ctx)
        }
    }

    override fun StringBuilder.compileVal(node: NodeNodesList, compiler: Compiler, ctx: CompilationContext): Variable {
        append("[&]() {\nif (")
        compiler.compileVal(node.nodes[0], ctx)
        append(")\n return ")
        val result = compiler.compileVal(node.nodes[1], ctx).apply { load(this@compileVal) }.type
        if (node.nodes.size == 3) {
            append(";\nelse\n return ")
            compiler.compile(node.nodes[2], ctx)
        }
        append(";\n}()")
        return Variable.tmp(node, result)
    }
}