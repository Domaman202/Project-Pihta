package ru.DmN.pht.std.util.compiler.java.compilers

import ru.DmN.pht.base.compiler.java.Compiler
import ru.DmN.pht.base.compiler.java.ctx.CompilationContext
import ru.DmN.pht.base.parser.ast.NodeNodesList
import ru.DmN.pht.base.utils.Variable
import ru.DmN.pht.base.utils.VirtualType
import ru.DmN.pht.std.base.compiler.java.compilers.IStdNodeCompiler
import ru.DmN.pht.std.base.compiler.java.utils.ComputeType
import ru.DmN.pht.std.base.compiler.java.utils.method
import java.util.*
import kotlin.math.absoluteValue

object NCRandSymbol : IStdNodeCompiler<NodeNodesList> {
    override fun calc(node: NodeNodesList, compiler: Compiler, ctx: CompilationContext): VirtualType =
        VirtualType.STRING

    override fun compile(node: NodeNodesList, compiler: Compiler, ctx: CompilationContext, ret: Boolean): Variable? =
        if (ret) {
            ctx.method.node.visitLdcInsn(gen())
            Variable("pht$${node.hashCode()}", "java.lang.String", -1, true)
        } else null

    override fun compute(node: NodeNodesList, compiler: Compiler, ctx: CompilationContext, type: ComputeType): Any =
        if (type == ComputeType.NAME)
            gen()
        else node

    private fun gen() = Random().nextInt().absoluteValue.toString()
}