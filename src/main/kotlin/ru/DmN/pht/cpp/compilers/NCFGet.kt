package ru.DmN.pht.cpp.compilers

import ru.DmN.pht.ast.NodeFGet
import ru.DmN.pht.cpp.compiler.utils.load
import ru.DmN.pht.cpp.compiler.utils.name
import ru.DmN.siberia.compiler.Compiler
import ru.DmN.siberia.compiler.ctx.CompilationContext
import ru.DmN.siberia.utils.Variable

object NCFGet : ICppNRCompiler<NodeFGet> {
    override fun StringBuilder.compileVal(node: NodeFGet, compiler: Compiler, ctx: CompilationContext): Variable {
        val static: Boolean
        when (node.type) {
            NodeFGet.Type.UNKNOWN -> throw UnsupportedOperationException()
            NodeFGet.Type.STATIC -> {
                append(node.vtype.name()).append("::").append(node.name)
                if (node.name == "INSTANCE")
                    append("()")
                static = true
            }
            NodeFGet.Type.INSTANCE -> {
                compiler.compileVal(node.nodes[0], ctx).load(this)
                append("->").append(node.name)
                static = false
            }
        }
        return Variable.tmp(node, node.vtype.fields.find { it.name == node.name && it.modifiers.isStatic == static }!!.type)
    }
}