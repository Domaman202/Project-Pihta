package ru.DmN.pht.std.base.compiler.java.compilers

import ru.DmN.pht.base.compiler.java.Compiler
import ru.DmN.pht.base.compiler.java.ctx.CompilationContext
import ru.DmN.pht.base.parser.ast.NodeNodesList
import ru.DmN.pht.base.utils.Variable
import ru.DmN.pht.std.base.compiler.java.utils.method
import ru.DmN.pht.std.base.utils.load

object NCYield : IStdNodeCompiler<NodeNodesList> {
    override fun compile(node: NodeNodesList, compiler: Compiler, ctx: CompilationContext, ret: Boolean): Variable? =
        if (ret)
            compiler.compile(node.nodes.first(), ctx, true)?.apply { load(this, ctx.method.node) }
        else null
}