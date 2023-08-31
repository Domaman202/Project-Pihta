package ru.DmN.pht.std.util.compiler.java.compilers

import org.objectweb.asm.Type
import ru.DmN.pht.base.Compiler
import ru.DmN.pht.base.compiler.java.ctx.CompilationContext
import ru.DmN.pht.base.parser.ast.NodeNodesList
import ru.DmN.pht.base.utils.Variable
import ru.DmN.pht.base.utils.VirtualType
import ru.DmN.pht.base.utils.desc
import ru.DmN.pht.std.base.compiler.java.compilers.IStdNodeCompiler
import ru.DmN.pht.std.base.compiler.java.utils.ComputeType
import ru.DmN.pht.std.base.compiler.java.utils.computeName
import ru.DmN.pht.std.base.compiler.java.utils.method

object NCClass : IStdNodeCompiler<NodeNodesList> {
    override fun calc(node: NodeNodesList, compiler: Compiler, ctx: CompilationContext): VirtualType =
        VirtualType.CLASS

    override fun compile(node: NodeNodesList, compiler: Compiler, ctx: CompilationContext, ret: Boolean): Variable? =
        if (ret)
            ctx.method.node.run {
                visitLdcInsn(Type.getType(compiler.computeName(node.nodes.first(), ctx).desc))
                Variable("pht$${node.hashCode()}", "java.lang.Class", -1, true)
            }
        else null

    override fun compute(node: NodeNodesList, compiler: Compiler, ctx: CompilationContext, type: ComputeType): Any =
        if (type == ComputeType.NAME)
            compiler.computeName(node.nodes.first(), ctx)
        else node
}