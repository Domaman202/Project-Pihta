package ru.DmN.pht.jvm.compilers

import ru.DmN.pht.ast.NodeSet
import ru.DmN.pht.compiler.java.utils.load
import ru.DmN.pht.compiler.java.utils.storeCast
import ru.DmN.pht.jvm.compiler.ctx.body
import ru.DmN.pht.jvm.compiler.ctx.method
import ru.DmN.pht.utils.normalizeName
import ru.DmN.siberia.compiler.Compiler
import ru.DmN.siberia.compiler.ctx.CompilationContext
import ru.DmN.siberia.compilers.INodeCompiler

object NCSet : INodeCompiler<NodeSet> {
    override fun compile(node: NodeSet, compiler: Compiler, ctx: CompilationContext) {
        ctx.method.node.run {
            val value = compiler.compileVal(node.nodes[0], ctx)
            load(value, this)
            storeCast(ctx.body[node.name.normalizeName()]!!, value.type, this)
        }
    }
}