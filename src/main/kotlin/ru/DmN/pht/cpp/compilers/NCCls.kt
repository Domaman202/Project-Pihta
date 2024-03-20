package ru.DmN.pht.compiler.cpp.compilers

import ru.DmN.pht.ast.NodeType
import ru.DmN.pht.cpp.compiler.utils.name
import ru.DmN.pht.cpp.compiler.utils.nameType
import ru.DmN.pht.cpp.compilers.ICppNRCompiler
import ru.DmN.pht.jvm.utils.vtype.superclass
import ru.DmN.pht.processor.ctx.with
import ru.DmN.siberia.compiler.Compiler
import ru.DmN.siberia.compiler.ctx.CompilationContext
import ru.DmN.siberia.utils.vtype.simpleName

object NCCls : ICppNRCompiler<NodeType> {
    override fun StringBuilder.compile(node: NodeType, compiler: Compiler, ctx: CompilationContext) {
        if (node.type.isFile)
            node.nodes.forEachIndexed { i, it ->
                if (i > 0)
                    append('\n')
                compiler.compile(it, ctx)
            }
        else {
            compileHead(node, compiler, ctx)
            append("};\n\n")
            compileTail(node, compiler, ctx)
        }
    }

    fun StringBuilder.compileHead(node: NodeType, compiler: Compiler, ctx: CompilationContext) {
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
                append("\nvoid set_age(uint8_t value) override {\n").append(superclass!!.name()).append("::set_age(value);\n")
                fields.forEach { append(it.name).append("->set_age(value);\n") }
                append("}\n")
            }
        }
    }

    fun StringBuilder.compileTail(node: NodeType, compiler: Compiler, ctx: CompilationContext) {
        node.type.run {
            fields.stream().filter { it.modifiers.isStatic }.forEach {
                append(it.type.nameType()).append(' ').append(name()).append("::").append(it.name).append(";\n")
            }
        }
        append('\n')
    }
}