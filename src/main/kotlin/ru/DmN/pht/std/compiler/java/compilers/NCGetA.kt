package ru.DmN.pht.std.compiler.java.compilers

import ru.DmN.pht.base.compiler.java.Compiler
import ru.DmN.pht.base.compiler.java.compilers.NodeCompiler
import ru.DmN.pht.base.compiler.java.ctx.CompilationContext
import ru.DmN.pht.base.parser.ast.Node
import ru.DmN.pht.base.utils.Variable
import ru.DmN.pht.base.utils.VirtualType

object NCGetA : NodeCompiler<Node>() {
    override fun calc(node: Node, compiler: Compiler, ctx: CompilationContext): VirtualType? =
        if (ctx.type.method && ctx.type.body) {
            val name = compiler.computeStringConst(node.nodes.first(),ctx)
            ctx.global.getType(compiler, ctx.body!!.variables.find { it.name == name }!!.type ?: "java.lang.Object")
        } else null

    override fun compile(node: Node, compiler: Compiler, ctx: CompilationContext, ret: Boolean): Variable? =
        if (ret && ctx.type.method && ctx.type.body) {
            val name = compiler.computeStringConst(node.nodes.first(),ctx)
            ctx.body!![name]!!
        } else null
}