package ru.DmN.phtx.pcl.compiler.json.compilers

import ru.DmN.phtx.pcl.ast.INodeArray
import ru.DmN.phtx.pcl.ast.NodeElement
import ru.DmN.phtx.pcl.compiler.json.utils.indent
import ru.DmN.phtx.pcl.compiler.json.utils.out
import ru.DmN.siberia.Compiler
import ru.DmN.siberia.compiler.ctx.CompilationContext
import ru.DmN.siberia.compilers.INodeCompiler

object NCArray : INodeCompiler<NodeElement> {
    override fun compile(node: NodeElement, compiler: Compiler, ctx: CompilationContext) {
        val out = ctx.out
        val indent = ctx.indent
        //
        out.append('"').append(node.name).append("\": {")
        node as INodeArray
        if (node.size > 0) {
            var i = 0
            node.forEach {
                out.append('\n').append("\t".repeat(indent + 1))
                compiler.compile(it, ctx)
                if (++i < node.size) {
                    out.append(',')
                }
            }
            out.append('\n').append("\t".repeat(indent))
        }
        out.append('}')
    }
}