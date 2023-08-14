package ru.DmN.pht.std.compiler.java.compilers

import org.objectweb.asm.Label
import org.objectweb.asm.Opcodes
import ru.DmN.pht.base.compiler.java.Compiler
import ru.DmN.pht.base.compiler.java.compilers.NodeCompiler
import ru.DmN.pht.base.compiler.java.ctx.CompilationContext
import ru.DmN.pht.base.utils.Variable
import ru.DmN.pht.base.utils.VirtualType
import ru.DmN.pht.base.parser.ast.NodeNodesList
import ru.DmN.pht.std.utils.load

object NCIf : NodeCompiler<NodeNodesList>() {
    override fun calcType(node: NodeNodesList, compiler: Compiler, ctx: CompilationContext): VirtualType? =
        if (ctx.type.method)
            node.nodes.map { compiler.calc(it, ctx) }.let { ru.DmN.pht.std.utils.calcType(it[1], it[2]) }.first
        else null

    override fun compile(node: NodeNodesList, compiler: Compiler, ctx: CompilationContext, ret: Boolean): Variable? {
        return if (ctx.type.method)
            ctx.mctx!!.node.run {
                compiler.compile(node.nodes.first(), ctx, true)
                val labelIf = Label()
                visitJumpInsn(Opcodes.IFEQ, labelIf)
                val typeA = compiler.compile(node.nodes[1], ctx, ret)?.apply { load(this, ctx.mctx.node) }?.type?.let { ctx.gctx.getType(compiler, it) }
                val labelExit = Label()
                visitJumpInsn(Opcodes.GOTO, labelExit)
                visitLabel(labelIf)
                val typeB = if (node.nodes.size == 3)
                    compiler.compile(node.nodes[2], ctx, ret)?.apply { load(this, ctx.mctx.node) }?.type?.let { ctx.gctx.getType(compiler, it) }
                else null
                visitLabel(labelExit)
                if (ret)
                    Variable("pht$${node.hashCode()}", ru.DmN.pht.std.utils.calcType(typeA, typeB).first?.name, -1, true)
                else null
            }
        else null
    }
}