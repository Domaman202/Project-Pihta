package ru.DmN.pht.compiler.java.compilers

import ru.DmN.pht.ast.NodeInner
import ru.DmN.pht.std.compiler.java.utils.clazz
import ru.DmN.siberia.Compiler
import ru.DmN.siberia.compiler.ctx.CompilationContext
import ru.DmN.siberia.compilers.INodeCompiler
import ru.DmN.siberia.utils.className

object NCInner : INodeCompiler<NodeInner> {
    override fun compile(node: NodeInner, compiler: Compiler, ctx: CompilationContext) {
        val inner = ctx.clazz.node
        val outer = compiler.classes[node.type]!!
        outer.visitInnerClass(node.field, outer.name, inner.name, inner.access)
    }
}