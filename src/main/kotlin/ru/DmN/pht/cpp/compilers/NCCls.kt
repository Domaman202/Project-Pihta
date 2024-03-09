package ru.DmN.pht.compiler.cpp.compilers

import ru.DmN.pht.ast.NodeType
import ru.DmN.pht.cpp.compiler.utils.name
import ru.DmN.pht.cpp.compilers.ICppNRCompiler
import ru.DmN.pht.processor.ctx.with
import ru.DmN.siberia.compiler.Compiler
import ru.DmN.siberia.compiler.ctx.CompilationContext

object NCCls : ICppNRCompiler<NodeType> {
    override fun StringBuilder.compile(node: NodeType, compiler: Compiler, ctx: CompilationContext) {
        if (node.type.isFile)
            node.nodes.forEachIndexed { i, it ->
                if (i > 0)
                    append('\n')
                compiler.compile(it, ctx)
            }
        else {
            compile0(node, compiler, ctx)
            append("};\n\n")
        }
    }

    fun StringBuilder.compile0(node: NodeType, compiler: Compiler, ctx: CompilationContext) {
        node.type.run {
            append("class ").append(name).append(" : public ")
            parents.forEachIndexed { i, it ->
                if (i > 0)
                    append(", ")
                append(it.name())
            }
            append(" {\npublic:\n")
            val context = ctx.with(this)
            node.nodes.forEachIndexed { i, it ->
                if (i > 0)
                    append('\n')
                compiler.compile(it, context)
            }
            append("protected:\nexplicit ").append(simpleName).append("(nullptr_t) : ").append(superclass!!.name())
                .append("(nullptr) { }\n")
            val fields = fields.filter { !(it.type.isPrimitive || it.modifiers.isStatic) }
            if (fields.isNotEmpty()) {
                append("\nvoid set_age(uint8_t value) override {\n{${superclass!!.name()}}::set_age(value);\n")
                fields.forEach { append(it.name).append("->set_age(value);\n") }
                append("}\n")
            }
        }
    }
}