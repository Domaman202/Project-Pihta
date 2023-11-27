package ru.DmN.siberia.compilers

import ru.DmN.siberia.Compiler
import ru.DmN.siberia.ast.NodeNodesList
import ru.DmN.siberia.compiler.ctx.CompilationContext
import ru.DmN.siberia.utils.Variable

/**
 * Компилятор нод с под-нодами.
 */
object NCDefault : INodeCompiler<NodeNodesList> {
    override fun compile(node: NodeNodesList, compiler: Compiler, ctx: CompilationContext) =
        node.nodes.forEach { compiler.compile(it, ctx) }

    override fun compileVal(node: NodeNodesList, compiler: Compiler, ctx: CompilationContext): Variable {
        node.nodes.dropLast(1).forEach { compiler.compile(it, ctx) }
        return compiler.compileVal(node.nodes.last(), ctx)
    }
}