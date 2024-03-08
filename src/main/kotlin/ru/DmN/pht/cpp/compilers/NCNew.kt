package ru.DmN.pht.cpp.compilers

import ru.DmN.pht.ast.NodeNew
import ru.DmN.pht.cpp.compiler.utils.load
import ru.DmN.pht.cpp.compiler.utils.name
import ru.DmN.siberia.ast.INodesList
import ru.DmN.siberia.compiler.Compiler
import ru.DmN.siberia.compiler.ctx.CompilationContext
import ru.DmN.siberia.utils.Variable

object NCNew : ICppCompiler<NodeNew> {
    override fun StringBuilder.compileVal(node: NodeNew, compiler: Compiler, ctx: CompilationContext): Variable {
        append("gc.alloc_ptr<").append(node.type.name()).append('>')
        compileArgs(node, compiler, ctx)
        return Variable.tmp(node, node.type)
    }

    fun StringBuilder.compileArgs(node: INodesList, compiler: Compiler, ctx: CompilationContext) {
        append('(')
        node.nodes.forEachIndexed { i, it ->
            if (i > 1)
                append(", ")
            compiler.compileVal(it, ctx).load(this)
        }
        append(')')
    }
}