package ru.DmN.pht.cpp.compilers

import ru.DmN.pht.ast.NodeNew
import ru.DmN.pht.cpp.compiler.utils.load
import ru.DmN.pht.cpp.utils.vtype.VVTAutoPointer
import ru.DmN.pht.cpp.utils.vtype.normalizedName
import ru.DmN.pht.utils.vtype.PhtVirtualType
import ru.DmN.siberia.ast.INodesList
import ru.DmN.siberia.compiler.Compiler
import ru.DmN.siberia.compiler.ctx.CompilationContext
import ru.DmN.siberia.utils.Variable

object NCNew : ICppCompiler<NodeNew> {
    override fun StringBuilder.compileVal(node: NodeNew, compiler: Compiler, ctx: CompilationContext): Variable {
        append("gc.alloc_ptr<").append(node.type.componentType!!.normalizedName()).append('>')
        compileArgs(node, compiler, ctx)
        return Variable.tmp(node, VVTAutoPointer(node.type as PhtVirtualType))
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