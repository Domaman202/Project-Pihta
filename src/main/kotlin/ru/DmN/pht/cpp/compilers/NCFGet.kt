package ru.DmN.pht.cpp.compilers

import ru.DmN.pht.ast.NodeFGet
import ru.DmN.pht.cpp.compiler.utils.load
import ru.DmN.pht.cpp.compiler.utils.name
import ru.DmN.siberia.compiler.Compiler
import ru.DmN.siberia.compiler.ctx.CompilationContext
import ru.DmN.siberia.utils.Variable

object NCFGet : ICppNRCompiler<NodeFGet> {
    override fun StringBuilder.compileVal(node: NodeFGet, compiler: Compiler, ctx: CompilationContext): Variable {
        when (node.type) {
            NodeFGet.Type.UNKNOWN -> throw UnsupportedOperationException()
            NodeFGet.Type.STATIC -> {
                append(node.vtype.name()).append("::").append(node.name)
                if (node.name == "INSTANCE") {
                    append("()")
                }
            }
            NodeFGet.Type.INSTANCE -> {
                compiler.compileVal(node.nodes[0], ctx).load(this)
                append("->").append(node.name)
            }
        }
        return Variable.tmp(node, node.vtype)
    }
}