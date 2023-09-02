package ru.DmN.pht.std.base.compiler.java.compilers

import org.objectweb.asm.Label
import org.objectweb.asm.Opcodes
import ru.DmN.pht.base.compiler.java.Compiler
import ru.DmN.pht.base.compiler.java.ctx.CompilationContext
import ru.DmN.pht.base.parser.ast.NodeNodesList
import ru.DmN.pht.base.utils.Variable
import ru.DmN.pht.std.base.compiler.java.utils.method

object NCRepeat : IStdNodeCompiler<NodeNodesList> {
    override fun compile(node: NodeNodesList, compiler: Compiler, ctx: CompilationContext, ret: Boolean): Variable? =
        ctx.method.node.run {
            val labelStart = Label()
            visitLabel(labelStart)
            node.nodes.drop(1).forEach { compiler.compile(it, ctx, false) }
            compiler.compile(node.nodes.first(), ctx, true)
            visitJumpInsn(Opcodes.IFNE, labelStart)
            null
        }
}