package ru.DmN.pht.test.bf.compiler.java.compilers

import org.objectweb.asm.Opcodes
import ru.DmN.pht.base.compiler.java.Compiler
import ru.DmN.pht.base.compiler.java.compilers.INodeCompiler
import ru.DmN.pht.base.compiler.java.ctx.CompilationContext
import ru.DmN.pht.base.parser.ast.Node
import ru.DmN.pht.base.utils.Variable
import ru.DmN.pht.std.base.compiler.java.utils.method
import ru.DmN.pht.test.bf.compiler.java.utils.bf

object NCInc : INodeCompiler<Node> {
    override fun compile(node: Node, compiler: Compiler, ctx: CompilationContext, ret: Boolean): Variable? {
        val mctx = ctx.method
        val bctx = ctx.bf
        mctx.node.run {
            visitVarInsn(Opcodes.ALOAD, bctx.array.id)
            visitVarInsn(Opcodes.ILOAD, bctx.index.id)
            visitInsn(Opcodes.DUP2)
            visitInsn(Opcodes.IALOAD)
            visitInsn(Opcodes.ICONST_1)
            visitInsn(Opcodes.IADD)
            visitInsn(Opcodes.IASTORE)
        }
        return null
    }
}