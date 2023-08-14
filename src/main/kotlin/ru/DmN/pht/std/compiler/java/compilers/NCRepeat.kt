package ru.DmN.pht.std.compiler.java.compilers

import org.objectweb.asm.Label
import org.objectweb.asm.Opcodes
import ru.DmN.pht.base.Compiler
import ru.DmN.pht.base.compiler.java.compilers.NodeCompiler
import ru.DmN.pht.base.compiler.java.ctx.CompilationContext
import ru.DmN.pht.base.utils.Variable
import ru.DmN.pht.base.parser.ast.NodeNodesList

object NCRepeat : NodeCompiler<NodeNodesList>() {
    override fun compile(node: NodeNodesList, compiler: Compiler, ctx: CompilationContext, ret: Boolean): Variable? =
        if (ctx.type.method)
            ctx.mctx!!.node.run {
                val labelStart = Label()
                visitLabel(labelStart)
                node.nodes.drop(1).forEach { compiler.compile(it, ctx, false) }
                compiler.compile(node.nodes.first(), ctx, true)
                visitJumpInsn(Opcodes.IFNE, labelStart)
                null
            }
        else null
}