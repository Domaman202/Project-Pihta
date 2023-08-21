package ru.DmN.pht.std.base.compiler.java.compilers

import ru.DmN.pht.base.compiler.java.Compiler
import ru.DmN.pht.base.compiler.java.ctx.CompilationContext
import ru.DmN.pht.base.parser.ast.NodeNodesList
import ru.DmN.pht.base.utils.Variable
import ru.DmN.pht.base.utils.VirtualType
import ru.DmN.pht.std.base.compiler.java.utils.global
import ru.DmN.pht.std.base.compiler.java.utils.method

object NCNsName : IStdNodeCompiler<NodeNodesList> {
    override fun calc(node: NodeNodesList, compiler: Compiler, ctx: CompilationContext): VirtualType =
        VirtualType.STRING

    override fun compile(node: NodeNodesList, compiler: Compiler, ctx: CompilationContext, ret: Boolean): Variable? =
        if (ret) {
            ctx.method.node.visitLdcInsn(ctx.global.namespace)
            Variable("pht$${node.hashCode()}", "java.lang.String", -1, true)
        } else null
}