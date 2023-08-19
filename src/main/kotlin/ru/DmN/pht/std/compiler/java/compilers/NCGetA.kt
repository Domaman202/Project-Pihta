package ru.DmN.pht.std.compiler.java.compilers

import ru.DmN.pht.base.compiler.java.Compiler
import ru.DmN.pht.base.compiler.java.compilers.NodeCompiler
import ru.DmN.pht.base.compiler.java.ctx.CompilationContext
import ru.DmN.pht.base.parser.ast.Node
import ru.DmN.pht.base.utils.Variable
import ru.DmN.pht.base.utils.VirtualType
import ru.DmN.pht.std.compiler.java.ctx.body
import ru.DmN.pht.std.compiler.java.ctx.global

object NCGetA : NodeCompiler<Node>() {
    override fun calc(node: Node, compiler: Compiler, ctx: CompilationContext): VirtualType? {
        val name = compiler.computeStringConst(node.nodes.first(), ctx)
        return ctx.global.getType(compiler, ctx.body.variables.find { it.name == name }!!.type ?: "java.lang.Object")
    }

    override fun compile(node: Node, compiler: Compiler, ctx: CompilationContext, ret: Boolean): Variable? =
        if (ret) {
            val name = compiler.computeStringConst(node.nodes.first(),ctx)
            ctx.body[name]!!
        } else null
}