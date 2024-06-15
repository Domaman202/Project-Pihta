package ru.DmN.pht.cpp.compilers

import ru.DmN.pht.cpp.compiler.utils.load
import ru.DmN.pht.processor.ctx.isBody
import ru.DmN.siberia.ast.INodesList
import ru.DmN.siberia.compiler.Compiler
import ru.DmN.siberia.compiler.ctx.CompilationContext
import ru.DmN.siberia.compilers.NCDefault
import ru.DmN.siberia.utils.Variable

object NCProgn : ICppCompiler<INodesList> {
    override fun StringBuilder.compile(node: INodesList, compiler: Compiler, ctx: CompilationContext) {
        if (ctx.isBody()) {
            append("{\n")
            node.nodes.forEach {
                compiler.compile(it, ctx)
                append(";\n")
            }
            append('}')
        } else NCDefault.compile(node, compiler, ctx)
    }

    override fun StringBuilder.compileVal(node: INodesList, compiler: Compiler, ctx: CompilationContext): Variable {
        append("[&]() {\n")
        node.nodes.dropLast(1).forEach {
            compiler.compile(it, ctx)
            append(";\n")
        }
        append("return ")
        return compiler.compileVal(node.nodes.last(), ctx).run {
            load(this@compileVal)
            append(";\n}()")
            Variable.tmp(node, type)
        }
    }
}