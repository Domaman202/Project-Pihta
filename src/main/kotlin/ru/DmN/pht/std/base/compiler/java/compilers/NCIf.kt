package ru.DmN.pht.std.base.compiler.java.compilers

import org.objectweb.asm.Label
import org.objectweb.asm.Opcodes
import ru.DmN.pht.base.compiler.java.Compiler
import ru.DmN.pht.base.compiler.java.ctx.CompilationContext
import ru.DmN.pht.base.parser.ast.NodeNodesList
import ru.DmN.pht.base.utils.Variable
import ru.DmN.pht.base.utils.VirtualType
import ru.DmN.pht.std.base.utils.calcType
import ru.DmN.pht.std.base.compiler.java.utils.global
import ru.DmN.pht.std.base.compiler.java.utils.method
import ru.DmN.pht.std.base.utils.load

object NCIf : IStdNodeCompiler<NodeNodesList> {
    override fun calc(node: NodeNodesList, compiler: Compiler, ctx: CompilationContext): VirtualType? =
        node.nodes.map { compiler.calc(it, ctx) }.let { calcType(it[1], it[2]) }.first

    override fun compile(node: NodeNodesList, compiler: Compiler, ctx: CompilationContext, ret: Boolean): Variable? =
        ctx.method.node.run {
            val gctx = ctx.global
            compiler.compile(node.nodes.first(), ctx, true)
            val labelIf = Label()
            visitJumpInsn(Opcodes.IFEQ, labelIf)
            val typeA = compiler.compile(node.nodes[1], ctx, ret)?.apply { load(this, this@run) }?.type?.let { gctx.getType(compiler, it) }
            val labelExit = Label()
            visitJumpInsn(Opcodes.GOTO, labelExit)
            visitLabel(labelIf)
            val typeB = if (node.nodes.size == 3)
                compiler.compile(node.nodes[2], ctx, ret)?.apply { load(this, this@run) }?.type?.let { gctx.getType(compiler, it) }
            else null
            visitLabel(labelExit)
            if (ret)
                Variable("pht$${node.hashCode()}", calcType(typeA, typeB).first?.name, -1, true)
            else null
        }
}