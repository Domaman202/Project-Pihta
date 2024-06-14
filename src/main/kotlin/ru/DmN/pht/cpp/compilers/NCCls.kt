package ru.DmN.pht.compiler.cpp.compilers

import ru.DmN.pht.ast.NodeType
import ru.DmN.pht.cpp.compilers.ICppNRCompiler
import ru.DmN.pht.cpp.utils.vtype.normalizedName
import ru.DmN.pht.jvm.utils.vtype.superclass
import ru.DmN.pht.processor.ctx.with
import ru.DmN.pht.utils.meta.MetadataKeys
import ru.DmN.pht.utils.vtype.simpleName
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
            if (node.getMetadata(MetadataKeys.NATIVE) == true)
                compileHeadUniversal(node, compiler, ctx)
            else compileHeadPht(node, compiler, ctx)
            append("};\n\n")
            compileTail(node)
        }
    }

    private fun StringBuilder.compileHeadUniversal(node: NodeType, compiler: Compiler, ctx: CompilationContext) {
        node.type.run {
            append("class ").append(name)
            if (parents.isNotEmpty()) {
                append(" : public ")
                parents.forEachIndexed { i, it ->
                    if (i > 0)
                        append(", ")
                    append(it.normalizedName)
                }
            }
            append(" {\npublic:\n")
            val context = ctx.with(this)
            node.nodes.forEachIndexed { i, it ->
                if (i > 0)
                    append('\n')
                compiler.compile(it, context)
            }
        }
    }

    fun StringBuilder.compileHeadPht(node: NodeType, compiler: Compiler, ctx: CompilationContext) {
        node.type.run {
            compileHeadUniversal(node, compiler, ctx)
            append("protected:\nexplicit ").append(simpleName).append("(nullptr_t) : ").append(superclass!!.normalizedName)
                .append("(nullptr) { }\n")
            val fields = fields.filter { !(it.type.isPrimitive || it.modifiers.isStatic) }
            if (fields.isNotEmpty()) {
                append("\nvoid set_age(uint8_t value) override {\n").append(superclass!!.normalizedName).append("::set_age(value);\n")
                fields.forEach { append(it.name).append("->set_age(value);\n") }
                append("}\n")
            }
        }
    }

    fun StringBuilder.compileTail(node: NodeType) {
        node.type.run {
            fields.stream().filter { it.modifiers.isStatic }.forEach {
                append(it.type.normalizedName).append(' ').append(normalizedName).append("::").append(it.name).append(";\n")
            }
        }
        append('\n')
    }
}