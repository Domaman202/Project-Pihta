package ru.DmN.pht.example.bf.compiler.java.compilers

import org.objectweb.asm.Label
import org.objectweb.asm.Opcodes
import org.objectweb.asm.tree.InsnNode
import org.objectweb.asm.tree.LdcInsnNode
import ru.DmN.pht.base.compiler.java.Compiler
import ru.DmN.pht.base.compiler.java.compilers.INodeCompiler
import ru.DmN.pht.base.compiler.java.compilers.NCDefault
import ru.DmN.pht.base.compiler.java.ctx.CompilationContext
import ru.DmN.pht.base.parser.ast.NodeNodesList
import ru.DmN.pht.base.utils.Variable
import ru.DmN.pht.std.base.compiler.java.utils.body
import ru.DmN.pht.std.base.compiler.java.utils.method
import ru.DmN.pht.example.bf.compiler.java.ctx.BFContext
import ru.DmN.pht.example.bf.compiler.java.utils.with

object NCBF : INodeCompiler<NodeNodesList> {
    override fun compile(node: NodeNodesList, compiler: Compiler, ctx: CompilationContext, ret: Boolean): Variable? {
        val mctx = ctx.method
        val bctx = ctx.body
        mctx.node.run {
            val label = Label()
            visitLabel(label)
            val index = mctx.createVariable(bctx, "bf\$index", "int", label)
            val arr = mctx.createVariable(bctx, "bf\$array", "[I", label)
            val size = InsnNode(Opcodes.NOP)
            instructions.add(size)
            visitIntInsn(Opcodes.NEWARRAY, Opcodes.T_INT)
            visitVarInsn(Opcodes.ASTORE, arr.id)
            visitInsn(Opcodes.ICONST_0)
            visitVarInsn(Opcodes.ISTORE, index.id)
            val bfc = BFContext(index, arr)
            NCDefault.compile(node, compiler, ctx.with(bfc), false)
            instructions.set(size, LdcInsnNode(bfc.maxIndex + 1))
        }
        return null
    }
}