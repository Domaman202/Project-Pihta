package ru.DmN.pht.jvm.compilers

import ru.DmN.pht.ast.NodeInner
import ru.DmN.pht.jvm.compiler.ctx.classes
import ru.DmN.pht.jvm.compiler.ctx.clazz
import ru.DmN.siberia.compiler.Compiler
import ru.DmN.siberia.compiler.ctx.CompilationContext
import ru.DmN.siberia.compilers.INodeCompiler

object NCInner : INodeCompiler<NodeInner> {
    override fun compile(node: NodeInner, compiler: Compiler, ctx: CompilationContext) {
        val inner = ctx.clazz.node
        val outer = compiler.contexts.classes[node.type]!!
        outer.visitInnerClass(node.field, outer.name, inner.name, inner.access)
    }
}