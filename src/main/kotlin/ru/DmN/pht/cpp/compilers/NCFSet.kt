package ru.DmN.pht.cpp.compilers

import ru.DmN.pht.ast.NodeFSet
import ru.DmN.pht.cpp.compiler.utils.load
import ru.DmN.pht.cpp.compiler.utils.name
import ru.DmN.siberia.compiler.Compiler
import ru.DmN.siberia.compiler.ctx.CompilationContext

object NCFSet : ICppNRCompiler<NodeFSet> {
    override fun StringBuilder.compile(node: NodeFSet, compiler: Compiler, ctx: CompilationContext) {
        node.field.run {
            if (modifiers.isStatic)
                append(declaringClass.name()).append("::").append(name)
            else {
                compiler.compileVal(node.nodes[0], ctx).load(this@compile)
                append("->").append(name)
            }
            append(" = ")
            compiler.compileVal(node.nodes[1], ctx).load(this@compile)
        }
    }
}