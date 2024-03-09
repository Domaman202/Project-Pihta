package ru.DmN.pht.cpp.compilers

import ru.DmN.pht.ast.NodeDefn
import ru.DmN.pht.cpp.compiler.utils.load
import ru.DmN.pht.cpp.compiler.utils.name
import ru.DmN.pht.processor.ctx.with
import ru.DmN.siberia.ast.INodesList
import ru.DmN.siberia.compiler.Compiler
import ru.DmN.siberia.compiler.ctx.CompilationContext
import ru.DmN.siberia.utils.Variable
import ru.DmN.siberia.utils.vtype.VirtualMethod
import ru.DmN.siberia.utils.vtype.VirtualType.Companion.VOID

object NCDefn : ICppNRCompiler<NodeDefn> {
    override fun StringBuilder.compile(node: NodeDefn, compiler: Compiler, ctx: CompilationContext) {
        node.method.run {
            if (modifiers.file)
                node.nodes.forEach { compiler.compile(it, ctx) }
            else {
                if (modifiers.static)
                    append("static ")
                if (modifiers.abstract || !modifiers.final)
                    append("virtual ")
                append(rettype.name()).append(' ').append(name)
                insertArgs(this)
                if (modifiers.abstract)
                    append(" = 0;\n")
                else {
                    append(" {\n")
                    val context = ctx.with(this)
                    if (rettype == VOID)
                        compile(node as INodesList, compiler, context)
                    else {
                        append("return ")
                        compileVal(node as INodesList, compiler, context).load(this@compile)
                    }
                    append("}\n")
                }
            }
        }
    }

    fun StringBuilder.compile(node: INodesList, compiler: Compiler, ctx: CompilationContext) {
        node.nodes.forEach {
            compiler.compile(it, ctx)
            append(";\n")
        }
    }

    fun StringBuilder.compileVal(node: INodesList, compiler: Compiler, ctx: CompilationContext): Variable {
        node.nodes.dropLast(1).forEach {
            compiler.compile(it, ctx)
            append(";\n")
        }
        return compiler.compileVal(node.nodes.last(), ctx).apply {
            append(";\n")
        }
    }

    fun StringBuilder.insertArgs(method: VirtualMethod) {
        append('(')
        method.argsc.forEachIndexed { i, it ->
            if (i > 0)
                append(", ")
            append(it.name()).append(' ').append(method.argsn[i])
        }
        append(')')
    }
}