package ru.DmN.pht.std.util.compiler.java.compilers

import ru.DmN.pht.base.Compiler
import ru.DmN.pht.base.compiler.java.ctx.CompilationContext
import ru.DmN.pht.base.parser.ast.NodeNodesList
import ru.DmN.pht.base.utils.Variable
import ru.DmN.pht.base.utils.VirtualType
import ru.DmN.pht.std.base.compiler.java.compilers.IStdNodeCompiler
import ru.DmN.pht.std.base.compiler.java.utils.ComputeType
import ru.DmN.pht.std.base.compiler.java.utils.method

class NCCompileTimeName(val getter: (compiler: Compiler, ctx: CompilationContext) -> String) : IStdNodeCompiler<NodeNodesList> {
    override fun calc(node: NodeNodesList, compiler: Compiler, ctx: CompilationContext): VirtualType =
        VirtualType.STRING

    override fun compile(node: NodeNodesList, compiler: Compiler, ctx: CompilationContext, ret: Boolean): Variable? =
        if (ret) {
            ctx.method.node.visitLdcInsn(getter(compiler, ctx))
            Variable("pht$${node.hashCode()}", "java.lang.String", -1, true)
        } else null

    override fun compute(node: NodeNodesList, compiler: Compiler, ctx: CompilationContext, type: ComputeType): Any? =
        if (type == ComputeType.NAME)
            getter(compiler, ctx)
        else node
}