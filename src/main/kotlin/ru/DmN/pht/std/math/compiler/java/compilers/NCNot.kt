package ru.DmN.pht.std.math.compiler.java.compilers

import org.objectweb.asm.Label
import org.objectweb.asm.Opcodes
import ru.DmN.pht.base.compiler.java.Compiler
import ru.DmN.pht.base.compiler.java.compilers.NodeCompiler
import ru.DmN.pht.base.compiler.java.ctx.CompilationContext
import ru.DmN.pht.base.utils.Variable
import ru.DmN.pht.base.utils.VirtualType
import ru.DmN.pht.std.utils.load
import ru.DmN.pht.std.math.ast.NodeNot

object NCNot : NodeCompiler<NodeNot>() {
    override fun calc(node: NodeNot, compiler: Compiler, ctx: CompilationContext): VirtualType? =
        if (ctx.type.method)
            compiler.typeOf("boolean")
        else null

    override fun compile(node: NodeNot, compiler: Compiler, ctx: CompilationContext, ret: Boolean): Variable? =
        if (ctx.type.method) {
            ctx.method!!.node.apply {
                load(compiler.compile(node.value, ctx, true)!!, this)
                val labelIf = Label()
                visitJumpInsn(Opcodes.IFNE, labelIf)
                visitInsn(Opcodes.ICONST_1)
                val labelExit = Label()
                visitJumpInsn(Opcodes.GOTO, labelExit)
                visitLabel(labelIf)
                visitInsn(Opcodes.ICONST_0)
                visitLabel(labelExit)
            }
            Variable("pht$${node.hashCode()}", "boolean", -1, true)
        } else null
}