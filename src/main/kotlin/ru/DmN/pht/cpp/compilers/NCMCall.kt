package ru.DmN.pht.cpp.compilers

import ru.DmN.pht.ast.NodeMCall
import ru.DmN.pht.ast.NodeMCall.Type.*
import ru.DmN.pht.cpp.compiler.utils.load
import ru.DmN.pht.cpp.utils.vtype.normalizedName
import ru.DmN.siberia.ast.NodeNodesList
import ru.DmN.siberia.compiler.Compiler
import ru.DmN.siberia.compiler.ctx.CompilationContext
import ru.DmN.siberia.utils.Variable

object NCMCall : ICppCompiler<NodeMCall> {
    override fun StringBuilder.compileVal(node: NodeMCall, compiler: Compiler, ctx: CompilationContext): Variable {
        node.method.run {
            when (node.type) {
                UNKNOWN,
                DYNAMIC -> throw UnsupportedOperationException()
                EXTEND  -> TODO()
                STATIC  -> append(declaringClass.normalizedName()).append("::")
                VIRTUAL -> {
                    compiler.compileVal(node.instance, ctx).load(this@compileVal)
                    append("->")
                }
                SUPER -> TODO()
            }
            append(name)
            compileArgs(node, compiler, ctx)
            return Variable.tmp(node, node.generic ?: rettype)
        }
    }

    fun StringBuilder.compileArgs(node: NodeNodesList, compiler: Compiler, ctx: CompilationContext) {
        append('(')
        node.nodes.forEachIndexed { i, it ->
            if (i > 0)
                append(", ")
            compiler.compileVal(it, ctx).load(this)
        }
        append(')')
    }
}