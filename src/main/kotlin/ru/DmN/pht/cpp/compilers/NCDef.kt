package ru.DmN.pht.cpp.compilers

import ru.DmN.pht.ast.NodeDef
import ru.DmN.pht.cpp.compiler.utils.load
import ru.DmN.pht.cpp.compiler.utils.nameType
import ru.DmN.siberia.compiler.Compiler
import ru.DmN.siberia.compiler.ctx.CompilationContext

object NCDef : ICppCompiler<NodeDef> {
    override fun StringBuilder.compile(node: NodeDef, compiler: Compiler, ctx: CompilationContext) {
        if (node.isVariable) {
            node.variables.forEach {
                append(it.type.nameType()).append(' ').append(it.name)
                if (it.value != null) {
                    append(" = ")
                    compiler.compileVal(it.value, ctx).load(this)
                }
            }
        } else {
            node.variables.forEach {
                if (node.static)
                    append("static ")
                append(it.type.nameType()).append(' ').append(it.name).append(";\n")
            }
        }
    }
}