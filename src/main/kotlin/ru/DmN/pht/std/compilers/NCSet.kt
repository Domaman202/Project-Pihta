package ru.DmN.pht.std.compilers

import ru.DmN.pht.base.compiler.java.Compiler
import ru.DmN.pht.base.compiler.java.compilers.INodeCompiler
import ru.DmN.pht.base.compiler.java.utils.CompilationContext
import ru.DmN.pht.std.ast.NodeSet
import ru.DmN.pht.std.compiler.java.utils.body
import ru.DmN.pht.std.compiler.java.utils.load
import ru.DmN.pht.std.compiler.java.utils.method
import ru.DmN.pht.std.compiler.java.utils.storeCast

object NCSet : INodeCompiler<NodeSet> {
    override fun compile(node: NodeSet, compiler: Compiler, ctx: CompilationContext) {
        // todo: no variable set
        ctx.method.node.run {
            val value = compiler.compileVal(node.value!!, ctx)
            load(value, this)
            storeCast(ctx.body[node.name]!!, value.type(), this)
        }
    }
}