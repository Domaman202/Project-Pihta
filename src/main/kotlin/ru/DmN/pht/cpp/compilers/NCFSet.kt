package ru.DmN.pht.cpp.compilers

import ru.DmN.pht.ast.NodeFSet
import ru.DmN.pht.cpp.compiler.utils.load
import ru.DmN.pht.cpp.utils.vtype.normalizedName
import ru.DmN.siberia.compiler.Compiler
import ru.DmN.siberia.compiler.ctx.CompilationContext

object NCFSet : ICppNRCompiler<NodeFSet> {
    override fun StringBuilder.compile(node: NodeFSet, compiler: Compiler, ctx: CompilationContext) {
        node.field.run {
            if (modifiers.isStatic)
                append(declaringClass.normalizedName()).append("::").append(name)
            else {
                compiler.compileVal(node.nodes[0], ctx).load(this@compile)
                append("->").append(name)
            }
            append(" = ")
            if (compiler.compileVal(node.nodes[1], ctx).apply { load(this@compile) }.type?.isPrimitive != true) {
                append(".get()")
            }
        }
    }
}