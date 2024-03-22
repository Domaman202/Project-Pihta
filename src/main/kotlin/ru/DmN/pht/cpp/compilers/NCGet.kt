package ru.DmN.pht.cpp.compilers

import ru.DmN.pht.ast.NodeGet
import ru.DmN.pht.cpp.utils.vtype.normalizedName
import ru.DmN.pht.processor.ctx.clazz
import ru.DmN.siberia.compiler.Compiler
import ru.DmN.siberia.compiler.ctx.CompilationContext
import ru.DmN.siberia.utils.Variable

object NCGet : ICppCompiler<NodeGet> {
    override fun StringBuilder.compileVal(node: NodeGet, compiler: Compiler, ctx: CompilationContext): Variable =
        when (node.type) {
            NodeGet.Type.VARIABLE -> Variable(node.name, node.vtype, -1, false)
            NodeGet.Type.THIS_FIELD -> {
                append("this->").append(node.name)
                Variable.tmp(node, node.vtype)
            }
            NodeGet.Type.THIS_STATIC_FIELD -> {
                append(ctx.clazz.normalizedName()).append("::").append(node.name)
                Variable.tmp(node, node.vtype)
            }
        }
}