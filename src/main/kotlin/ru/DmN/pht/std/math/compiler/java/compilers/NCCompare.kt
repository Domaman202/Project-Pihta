package ru.DmN.pht.std.math.compiler.java.compilers

import org.objectweb.asm.Label
import org.objectweb.asm.Opcodes
import ru.DmN.pht.base.compiler.java.Compiler
import ru.DmN.pht.base.compiler.java.compilers.NodeCompiler
import ru.DmN.pht.base.compiler.java.ctx.CompilationContext
import ru.DmN.pht.base.utils.Variable
import ru.DmN.pht.base.utils.VirtualType
import ru.DmN.pht.std.utils.load
import ru.DmN.pht.std.math.ast.NodeEquals

object NCCompare : NodeCompiler<NodeEquals>() { // todo: list
    override fun calcType(node: NodeEquals, compiler: Compiler, ctx: CompilationContext): VirtualType? =
        if (ctx.type.method)
            compiler.typeOf("boolean")
        else null

    override fun compile(node: NodeEquals, compiler: Compiler, ctx: CompilationContext, ret: Boolean): Variable? =
        if (ret && ctx.type.method)
            ctx.method!!.node.run {
                val type = node.nodes.map { compiler.compile(it, ctx, true) }.map { it!!.apply { load(it, this@run) }.type }.first() // todo: cast to общий type
                val labelIf = Label()
                visitJumpInsn(when (type) {
                    "boolean" -> {
                        when (node.operation) {
                            NodeEquals.Operation.EQ -> Opcodes.IFEQ
                            NodeEquals.Operation.NE -> Opcodes.IFNE
                            NodeEquals.Operation.LT -> Opcodes.IFLT
                            NodeEquals.Operation.LE -> Opcodes.IFLE
                            NodeEquals.Operation.GT -> Opcodes.IFGT
                            NodeEquals.Operation.GE -> Opcodes.IFGE
                        }
                    }
                    "byte", "short", "char", "int" -> {
                        when (node.operation) {
                            NodeEquals.Operation.EQ -> Opcodes.IF_ICMPEQ
                            NodeEquals.Operation.NE -> Opcodes.IF_ICMPNE
                            NodeEquals.Operation.LT -> Opcodes.IF_ICMPLT
                            NodeEquals.Operation.LE -> Opcodes.IF_ICMPLE
                            NodeEquals.Operation.GT -> Opcodes.IF_ICMPGT
                            NodeEquals.Operation.GE -> Opcodes.IF_ICMPGE
                        }
                    }
                    else -> TODO() // obj compare
                }, labelIf)
                visitInsn(Opcodes.ICONST_0)
                val labelExit = Label()
                visitJumpInsn(Opcodes.GOTO, labelExit)
                visitLabel(labelIf)
                visitInsn(Opcodes.ICONST_1)
                visitLabel(labelExit)
                Variable("pht$${node.hashCode()}", "boolean", -1, true)
            }
        else null
}