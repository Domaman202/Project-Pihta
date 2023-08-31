package ru.DmN.pht.std.base.compiler.java.compilers

import org.objectweb.asm.Type
import ru.DmN.pht.base.Compiler
import ru.DmN.pht.base.compiler.java.ctx.CompilationContext
import ru.DmN.pht.base.parser.ast.NodeNodesList
import ru.DmN.pht.base.utils.Variable
import ru.DmN.pht.base.utils.VirtualType
import ru.DmN.pht.std.base.compiler.java.utils.method

object NCTypeof : IStdNodeCompiler<NodeNodesList> {
    override fun calc(node: NodeNodesList, compiler: Compiler, ctx: CompilationContext): VirtualType =
        VirtualType.CLASS

    override fun compile(node: NodeNodesList, compiler: Compiler, ctx: CompilationContext, ret: Boolean): Variable? =
        if (ret) ctx.method.node.run {
            visitLdcInsn(Type.getType(compiler.calc(node.nodes.first(), ctx)!!.desc))
            Variable("pht$${node.hashCode()}", "java.lang.Class", -1, true)
        } else null
}