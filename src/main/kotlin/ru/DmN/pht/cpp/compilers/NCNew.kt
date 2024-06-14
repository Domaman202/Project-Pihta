package ru.DmN.pht.cpp.compilers

import ru.DmN.pht.ast.NodeNew
import ru.DmN.pht.cpp.compiler.utils.load
import ru.DmN.pht.cpp.utils.vtype.isAutoPointer
import ru.DmN.pht.cpp.utils.vtype.normalizedName
import ru.DmN.siberia.ast.INodesList
import ru.DmN.siberia.compiler.Compiler
import ru.DmN.siberia.compiler.ctx.CompilationContext
import ru.DmN.siberia.utils.Variable

object NCNew : ICppCompiler<NodeNew> {
    override fun StringBuilder.compileVal(node: NodeNew, compiler: Compiler, ctx: CompilationContext): Variable {
        node.type.run {
            if (isAutoPointer)
                append("gc.alloc_ptr<").append(componentType!!.normalizedName).append('>')
            else append(normalizedName)
            compileArgs(node, compiler, ctx)
            return Variable.tmp(node, this)
        }
    }

    private fun StringBuilder.compileArgs(node: INodesList, compiler: Compiler, ctx: CompilationContext) {
        append('(')
        node.nodes.forEachIndexed { i, it ->
            if (i > 1)
                append(", ")
            compiler.compileVal(it, ctx).load(this)
        }
        append(')')
    }
}