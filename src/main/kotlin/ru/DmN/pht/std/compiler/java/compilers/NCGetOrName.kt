package ru.DmN.pht.std.compiler.java.compilers

import ru.DmN.pht.base.compiler.java.Compiler
import ru.DmN.pht.base.compiler.java.compilers.NodeCompiler
import ru.DmN.pht.base.compiler.java.ctx.CompilationContext
import ru.DmN.pht.base.utils.Variable
import ru.DmN.pht.base.utils.VirtualType
import ru.DmN.pht.std.ast.NodeGetOrName

object NCGetOrName : NodeCompiler<NodeGetOrName>() {
    override fun calc(node: NodeGetOrName, compiler: Compiler, ctx: CompilationContext): VirtualType? =
        NCGetB.calc(node, compiler, ctx)

    override fun compile(node: NodeGetOrName, compiler: Compiler, ctx: CompilationContext, ret: Boolean): Variable? =
        NCGetB.compile(node, compiler, ctx, ret)

    override fun compute(node: NodeGetOrName, compiler: Compiler, ctx: CompilationContext, name: Boolean): Any =
        if (name) node.name else node
}