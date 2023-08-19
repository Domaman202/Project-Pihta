package ru.DmN.pht.std.math.compiler.java.compilers

import org.objectweb.asm.Label
import org.objectweb.asm.Opcodes
import org.objectweb.asm.tree.MethodNode
import ru.DmN.pht.base.compiler.java.Compiler
import ru.DmN.pht.base.compiler.java.ctx.CompilationContext
import ru.DmN.pht.base.utils.Variable
import ru.DmN.pht.base.utils.VirtualType
import ru.DmN.pht.std.compiler.java.compilers.IStdNodeCompiler
import ru.DmN.pht.std.compiler.java.method
import ru.DmN.pht.std.math.ast.NodeEquals
import ru.DmN.pht.std.utils.load

object NCPrimitiveCompare : IStdNodeCompiler<NodeEquals> { // todo: list
    override fun calc(node: NodeEquals, compiler: Compiler, ctx: CompilationContext): VirtualType =
        VirtualType.BOOLEAN

    override fun compile(node: NodeEquals, compiler: Compiler, ctx: CompilationContext, ret: Boolean): Variable? =
        if (ret) {
            ctx.method.node.run {
                val type = node.nodes
                    .map { compiler.compile(it, ctx, true) }
                    .map { it!!.apply { load(it, this@run) }.type }.first() // todo: cast to общий type
                val labelIf = Label()
                when (type) {
                    "boolean", "byte", "short", "char", "int" -> {
                        visitJumpInsn(
                            when (node.operation) {
                                NodeEquals.Operation.EQ -> Opcodes.IF_ICMPEQ
                                NodeEquals.Operation.NE -> Opcodes.IF_ICMPNE
                                NodeEquals.Operation.LT -> Opcodes.IF_ICMPLT
                                NodeEquals.Operation.LE -> Opcodes.IF_ICMPLE
                                NodeEquals.Operation.GT -> Opcodes.IF_ICMPGT
                                NodeEquals.Operation.GE -> Opcodes.IF_ICMPGE
                            }, labelIf
                        )
                    }

                    "long" -> {
                        visitInsn(Opcodes.LCMP)
                        visitJumpInsn(
                            when (node.operation) {
                                NodeEquals.Operation.EQ -> Opcodes.IFNE
                                NodeEquals.Operation.NE -> Opcodes.IFEQ
                                NodeEquals.Operation.LT -> Opcodes.IFLE
                                NodeEquals.Operation.LE -> Opcodes.IFLT
                                NodeEquals.Operation.GT -> Opcodes.IFGE
                                NodeEquals.Operation.GE -> Opcodes.IFGT
                            }, labelIf
                        )
                    }

                    "float" -> {
                        insertA(this, node.operation, 1)
                        insertB(this, node.operation, labelIf)
                    }

                    "double" -> {
                        insertA(this, node.operation, 1)
                        insertB(this, node.operation, labelIf)
                    }

                    else -> {
                        visitMethodInsn(
                            Opcodes.INVOKESTATIC,
                            "java/util/Objects",
                            "equals",
                            "(Ljava/lang/Object;Ljava/lang/Object;)Z",
                            false
                        )
                        when (node.operation) {
                            NodeEquals.Operation.EQ -> {}
                            NodeEquals.Operation.NE -> {
                                visitInsn(Opcodes.ICONST_1)
                                visitInsn(Opcodes.IADD)
                            }

                            else -> throw UnsupportedOperationException()
                        }
                        return@run
                    }
                }
                visitInsn(Opcodes.ICONST_0)
                val labelExit = Label()
                visitJumpInsn(Opcodes.GOTO, labelExit)
                visitLabel(labelIf)
                visitInsn(Opcodes.ICONST_1)
                visitLabel(labelExit)
            }
            Variable("pht$${node.hashCode()}", "boolean", -1, true)
        } else null

    private fun insertA(node: MethodNode, operation: NodeEquals.Operation, offset: Int) = // todo: rename
        node.visitInsn(
            when (operation) {
                NodeEquals.Operation.EQ,
                NodeEquals.Operation.NE,
                NodeEquals.Operation.GT,
                NodeEquals.Operation.GE -> Opcodes.FCMPL + offset

                NodeEquals.Operation.LT,
                NodeEquals.Operation.LE -> Opcodes.FCMPG + offset
            }
        )

    private fun insertB(node: MethodNode, operation: NodeEquals.Operation, label: Label) = // todo: rename
        node.visitJumpInsn(
            when (operation) {
                NodeEquals.Operation.EQ -> Opcodes.IFEQ
                NodeEquals.Operation.NE -> Opcodes.IFNE
                NodeEquals.Operation.GT -> Opcodes.IFLE
                NodeEquals.Operation.GE -> Opcodes.IFLT
                NodeEquals.Operation.LT -> Opcodes.IFGE
                NodeEquals.Operation.LE -> Opcodes.IFGT
            }, label
        )
}
