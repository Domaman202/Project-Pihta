package ru.DmN.pht.base.compiler.java.compilers

import ru.DmN.pht.base.Compiler
import ru.DmN.pht.base.compiler.java.ctx.CompilationContext
import ru.DmN.pht.base.utils.Variable
import ru.DmN.pht.base.utils.VirtualType
import ru.DmN.pht.base.parser.ast.NodeNodesList

object NCNodesList : NodeCompiler<NodeNodesList>() {
    override fun calcType(node: NodeNodesList, compiler: Compiler, ctx: CompilationContext): VirtualType? =
        compiler.calc(node.nodes.last(), ctx)

    override fun compile(node: NodeNodesList, compiler: Compiler, ctx: CompilationContext, ret: Boolean): Variable? {
        node.nodes.dropLast(1).forEach { compiler.compile(it, ctx, false) }
        return compiler.compile(node.nodes.last(), ctx, ret)
    }
}