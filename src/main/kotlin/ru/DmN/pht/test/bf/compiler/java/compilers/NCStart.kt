package ru.DmN.pht.test.bf.compiler.java.compilers

import org.objectweb.asm.Label
import org.objectweb.asm.Opcodes
import ru.DmN.pht.base.compiler.java.Compiler
import ru.DmN.pht.base.compiler.java.compilers.INodeCompiler
import ru.DmN.pht.base.compiler.java.ctx.CompilationContext
import ru.DmN.pht.base.parser.ast.Node
import ru.DmN.pht.base.utils.Variable
import ru.DmN.pht.std.base.compiler.java.utils.method
import ru.DmN.pht.test.bf.compiler.java.utils.bf

object NCStart : INodeCompiler<Node> {
    override fun compile(node: Node, compiler: Compiler, ctx: CompilationContext, ret: Boolean): Variable? {
        val mctx = ctx.method
        val bctx = ctx.bf
        mctx.node.run {
            val start = Label()
            val stop = Label()
            visitLabel(start)
            visitVarInsn(Opcodes.ALOAD, bctx.array.id)
            visitVarInsn(Opcodes.ILOAD, bctx.index.id)
            visitInsn(Opcodes.IALOAD)
            visitJumpInsn(Opcodes.IFEQ, stop)
            bctx.stack.push(Pair(start, stop))
        }
        return null
    }
}