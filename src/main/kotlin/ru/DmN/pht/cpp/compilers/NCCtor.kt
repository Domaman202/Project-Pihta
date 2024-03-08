package ru.DmN.pht.cpp.compilers

import ru.DmN.pht.ast.NodeDefn
import ru.DmN.pht.cpp.compiler.utils.name
import ru.DmN.pht.cpp.compilers.NCDefn.compile
import ru.DmN.pht.cpp.compilers.NCDefn.insertArgs
import ru.DmN.pht.processor.ctx.with
import ru.DmN.siberia.ast.INodesList
import ru.DmN.siberia.compiler.Compiler
import ru.DmN.siberia.compiler.ctx.CompilationContext

object NCCtor : ICppCompiler<NodeDefn> {
    override fun StringBuilder.compile(node: NodeDefn, compiler: Compiler, ctx: CompilationContext) {
        node.method.run {
            append(declaringClass.simpleName)
            insertArgs(this)
            append(" : ").append(declaringClass.superclass!!.name()).append("(nullptr) {\n")
            val context = ctx.with(this)
            compile(node as INodesList, compiler, context)
            append("}\n")
        }
    }
}