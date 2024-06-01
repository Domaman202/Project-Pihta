package ru.DmN.pht.jvm.compilers

import ru.DmN.pht.ast.NodeTGet
import ru.DmN.pht.jvm.compiler.ctx.body
import ru.DmN.pht.utils.normalizeName
import ru.DmN.siberia.compiler.Compiler
import ru.DmN.siberia.compiler.ctx.CompilationContext
import ru.DmN.siberia.utils.Variable

object NCTGet : IValueNodeCompiler<NodeTGet> {
    override fun compileVal(node: NodeTGet, compiler: Compiler, ctx: CompilationContext): Variable {
        val name = node.name.normalizeName()
        return ctx.body.variables.find { it.type == node.type && it.name == name }!!
    }
}