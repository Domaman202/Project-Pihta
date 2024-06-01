package ru.DmN.pht.jvm.compilers

import ru.DmN.pht.ast.NodeBSet
import ru.DmN.pht.compiler.java.utils.load
import ru.DmN.pht.compiler.java.utils.storeCast
import ru.DmN.pht.jvm.compiler.ctx.method
import ru.DmN.siberia.compiler.Compiler
import ru.DmN.siberia.compiler.ctx.CompilationContext
import ru.DmN.siberia.compilers.INodeCompiler

object NCBSet : INodeCompiler<NodeBSet> {
    override fun compile(node: NodeBSet, compiler: Compiler, ctx: CompilationContext) {
        ctx.method.node.run {
            val value = compiler.compileVal(node.value, ctx)
            load(value, this)
            storeCast(node.variable, value.type, this)
        }
    }
}