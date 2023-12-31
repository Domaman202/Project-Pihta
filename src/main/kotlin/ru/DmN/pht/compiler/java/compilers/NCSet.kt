package ru.DmN.pht.std.compiler.java.compilers

import ru.DmN.pht.std.ast.NodeSet
import ru.DmN.pht.std.compiler.java.utils.body
import ru.DmN.pht.std.compiler.java.utils.load
import ru.DmN.pht.std.compiler.java.utils.method
import ru.DmN.pht.std.compiler.java.utils.storeCast
import ru.DmN.siberia.Compiler
import ru.DmN.siberia.compiler.ctx.CompilationContext
import ru.DmN.siberia.compilers.INodeCompiler

object NCSet : INodeCompiler<NodeSet> {
    override fun compile(node: NodeSet, compiler: Compiler, ctx: CompilationContext) {
        ctx.method.node.run {
            val value = compiler.compileVal(node.nodes[0], ctx)
            load(value, this)
            storeCast(ctx.body[node.name]!!, value.type(), this)
        }
    }
}