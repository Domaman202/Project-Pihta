package ru.DmN.pht.std.compiler.java.compilers

import ru.DmN.pht.base.compiler.java.Compiler
import ru.DmN.pht.base.compiler.java.ctx.CompilationContext
import ru.DmN.pht.base.parser.ast.Node
import ru.DmN.pht.base.utils.Variable
import ru.DmN.pht.base.utils.VirtualType
import ru.DmN.pht.std.compiler.java.utils.body
import ru.DmN.pht.std.compiler.java.utils.computeName
import ru.DmN.pht.std.compiler.java.utils.global

object NCGetA : IStdNodeCompiler<Node> {
    override fun calc(node: Node, compiler: Compiler, ctx: CompilationContext): VirtualType {
        val name = compiler.computeName(node.nodes.first(), ctx)
        return ctx.global.getType(compiler, ctx.body.variables.find { it.name == name }!!.type())
    }

    override fun compile(node: Node, compiler: Compiler, ctx: CompilationContext, ret: Boolean): Variable? =
        if (ret) {
            val name = compiler.computeName(node.nodes.first(),ctx)
            ctx.body[name]!!
        } else null
}