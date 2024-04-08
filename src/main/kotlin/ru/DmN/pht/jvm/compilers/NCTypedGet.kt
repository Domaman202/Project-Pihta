package ru.DmN.pht.jvm.compilers

import ru.DmN.pht.ast.NodeTypedGet
import ru.DmN.pht.jvm.compiler.ctx.body
import ru.DmN.pht.utils.normalizeName
import ru.DmN.siberia.compiler.Compiler
import ru.DmN.siberia.compiler.ctx.CompilationContext
import ru.DmN.siberia.compilers.INodeCompiler
import ru.DmN.siberia.utils.Variable

object NCTypedGet : INodeCompiler<NodeTypedGet> {
    override fun compileVal(node: NodeTypedGet, compiler: Compiler, ctx: CompilationContext): Variable {
        val name = node.name.normalizeName()
        return ctx.body.variables.find { it.type == node.type && it.name == name }!!
    }
}