package ru.DmN.pht.cpp.compilers

import ru.DmN.pht.ast.NodeType
import ru.DmN.pht.compiler.cpp.compilers.NCCls.compileTail
import ru.DmN.pht.cpp.utils.vtype.normalizedName
import ru.DmN.pht.processor.ctx.with
import ru.DmN.siberia.compiler.Compiler
import ru.DmN.siberia.compiler.ctx.CompilationContext

object NCItf : ICppNRCompiler<NodeType> {
    override fun StringBuilder.compile(node: NodeType, compiler: Compiler, ctx: CompilationContext) {
        node.type.run {
            append("class ").append(name)
            if (parents.isNotEmpty()) {
                append(" : public ")
                parents.forEachIndexed { i, it ->
                    if (i > 0)
                        append(", ")
                    append(it.normalizedName())
                }
            }
            append(" {\n")
            val context = ctx.with(this)
            node.nodes.forEachIndexed { i, it ->
                if (i > 0)
                    append('\n')
                compiler.compile(it, context)
            }
            append("};\n\n")
            compileTail(node)
        }
    }
}