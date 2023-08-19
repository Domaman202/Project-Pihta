package ru.DmN.pht.std.compiler.java.compilers

import org.objectweb.asm.Label
import org.objectweb.asm.Opcodes
import ru.DmN.pht.base.compiler.java.Compiler
import ru.DmN.pht.base.compiler.java.compilers.NodeCompiler
import ru.DmN.pht.base.compiler.java.ctx.CompilationContext
import ru.DmN.pht.base.utils.Variable
import ru.DmN.pht.base.parser.ast.NodeNodesList
import ru.DmN.pht.std.compiler.java.ctx.method

object NCWhile : NodeCompiler<NodeNodesList>() {
    override fun compile(node: NodeNodesList, compiler: Compiler, ctx: CompilationContext, ret: Boolean): Variable? =
        ctx.method.node.run {
            val labelStart = Label()
            visitLabel(labelStart)
            compiler.compile(node.nodes.first(), ctx, true)
            val labelExit = Label()
            visitJumpInsn(Opcodes.IFEQ, labelExit)
            node.nodes.drop(1).forEach { compiler.compile(it, ctx, false) }
            visitJumpInsn(Opcodes.GOTO, labelStart)
            visitLabel(labelExit)
            null
        }
}