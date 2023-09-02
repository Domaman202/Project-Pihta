package ru.DmN.pht.std.collections.compiler.java.compilers

import org.objectweb.asm.Opcodes
import ru.DmN.pht.base.compiler.java.Compiler
import ru.DmN.pht.base.compiler.java.ctx.CompilationContext
import ru.DmN.pht.base.parser.ast.NodeNodesList
import ru.DmN.pht.base.utils.Variable
import ru.DmN.pht.base.utils.VirtualType
import ru.DmN.pht.std.base.compiler.java.compilers.IStdNodeCompiler
import ru.DmN.pht.std.base.compiler.java.utils.method
import ru.DmN.pht.std.base.utils.load

class NCIntsOf(val type: VirtualType, val tid: Int, val iid: Int) : IStdNodeCompiler<NodeNodesList> {
    override fun calc(node: NodeNodesList, compiler: Compiler, ctx: CompilationContext): VirtualType =
        type.arrayType

    override fun compile(node: NodeNodesList, compiler: Compiler, ctx: CompilationContext, ret: Boolean): Variable? =
        if (ret)
            ctx.method.node.run {
                visitLdcInsn(node.nodes.size)
                visitIntInsn(Opcodes.NEWARRAY, tid)
                node.nodes.forEachIndexed { i, it ->
                    visitInsn(Opcodes.DUP)
                    visitLdcInsn(i)
                    load(compiler.compile(it, ctx, true)!!, this)
                    visitInsn(iid)
                }
                Variable("pht$${node.hashCode()}", type.arrayType.desc, -1, true)
            }
        else null
}