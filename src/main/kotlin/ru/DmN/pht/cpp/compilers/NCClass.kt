package ru.DmN.pht.compiler.cpp.compilers

import ru.DmN.pht.ast.NodeType
import ru.DmN.pht.cpp.compiler.utils.name
import ru.DmN.pht.cpp.compilers.ICppNRCompiler
import ru.DmN.pht.processor.ctx.with
import ru.DmN.siberia.compiler.Compiler
import ru.DmN.siberia.compiler.ctx.CompilationContext

object NCClass : ICppNRCompiler<NodeType> {
    override fun StringBuilder.compile(node: NodeType, compiler: Compiler, ctx: CompilationContext) {
        node.type.run {
            if (isFile)
                node.nodes.forEachIndexed { i, it ->
                    if (i > 0)
                        append('\n')
                    compiler.compile(it, ctx)
                }
            else {
                append("class ").append(name).append(" : public ")
                parents.forEach { append(it.name()).append(' ') }
                append("{\npublic:\n")
                val context = ctx.with(this)
                node.nodes.forEachIndexed { i, it ->
                    if (i > 0)
                        append('\n')
                    compiler.compile(it, context)
                }
                append("protected:\nexplicit ").append(simpleName).append("(nullptr_t) : ").append(superclass!!.name()).append("(nullptr) { }\n")
                val fields = fields.filter { !(it.type.isPrimitive || it.modifiers.isStatic) }
                if (fields.isNotEmpty()) {
                    append("\nvoid set_age(uint8_t value) override {\nthis->age = value;\n")
                    fields.forEach { append(it.name).append("->set_age(value);\n") }
                    append("}\n")
                }
                append("};\n\n")
            }
        }
    }
}