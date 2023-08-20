package ru.DmN.pht.std.collections.compiler.java.compilers

import org.objectweb.asm.Opcodes
import ru.DmN.pht.base.compiler.java.Compiler
import ru.DmN.pht.base.compiler.java.ctx.CompilationContext
import ru.DmN.pht.base.parser.ast.NodeNodesList
import ru.DmN.pht.base.utils.Variable
import ru.DmN.pht.base.utils.VirtualType
import ru.DmN.pht.std.compiler.java.compilers.IStdNodeCompiler
import ru.DmN.pht.std.compiler.java.utils.computeInt
import ru.DmN.pht.std.compiler.java.utils.method

class NCNewInts(val type: VirtualType, val tid: Int) : IStdNodeCompiler<NodeNodesList> {
    override fun calc(node: NodeNodesList, compiler: Compiler, ctx: CompilationContext): VirtualType =
        type.arrayType

    override fun compile(node: NodeNodesList, compiler: Compiler, ctx: CompilationContext, ret: Boolean): Variable? =
        if (ret)
            ctx.method.node.run {
                visitLdcInsn(compiler.computeInt(node.nodes.first(), ctx))
                visitIntInsn(Opcodes.NEWARRAY, tid)
                Variable("pht$${node.hashCode()}", type.arrayType.desc, -1, true)
            }
        else null
}