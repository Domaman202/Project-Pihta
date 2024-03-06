package ru.DmN.pht.jvm.compilers

import ru.DmN.pht.ast.NodeInner
import ru.DmN.pht.compiler.java.utils.classes
import ru.DmN.pht.compiler.java.utils.clazz
import ru.DmN.siberia.compiler.Compiler
import ru.DmN.siberia.compiler.ctx.CompilationContext
import ru.DmN.siberia.compilers.INodeCompiler
import ru.DmN.siberia.utils.className

object NCInner : INodeCompiler<NodeInner> {
    override fun compile(node: NodeInner, compiler: Compiler, ctx: CompilationContext) {
        val inner = ctx.clazz.node
        val outer = compiler.contexts.classes[node.type]!!
        outer.visitInnerClass(node.field, outer.name, inner.name, inner.access)
    }
}