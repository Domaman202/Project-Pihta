package ru.DmN.pht.std.compilers

import org.objectweb.asm.Label
import org.objectweb.asm.Opcodes
import org.objectweb.asm.tree.MethodNode
import ru.DmN.pht.base.compiler.java.Compiler
import ru.DmN.pht.base.compiler.java.compilers.INodeCompiler
import ru.DmN.pht.base.compiler.java.utils.CompilationContext
import ru.DmN.pht.base.utils.Variable
import ru.DmN.pht.base.utils.VirtualType
import ru.DmN.pht.std.ast.NodeEquals
import ru.DmN.pht.std.compiler.java.utils.load
import ru.DmN.pht.std.compiler.java.utils.method

object NCPrimitiveCompare : INodeCompiler<NodeEquals> {
    override fun compileVal(node: NodeEquals, compiler: Compiler, ctx: CompilationContext): Variable {
        ctx.method.node.run {
            val type = node.nodes
                .map { compiler.compileVal(it, ctx) }
                .map { it.apply { load(it, this@run) }.type }
                .first() // todo: cast to общий type
            val labelIf = Label()
            when (type) {
                VirtualType.BOOLEAN,
                VirtualType.BYTE,
                VirtualType.SHORT,
                VirtualType.CHAR,
                VirtualType.INT -> {
                    visitJumpInsn(
                        when (node.operation) {
                            NodeEquals.Operation.EQ -> Opcodes.IF_ICMPEQ
                            NodeEquals.Operation.NE -> Opcodes.IF_ICMPNE
                            NodeEquals.Operation.LT -> Opcodes.IF_ICMPGT
                            NodeEquals.Operation.LE -> Opcodes.IF_ICMPGE
                            NodeEquals.Operation.GT -> Opcodes.IF_ICMPLT
                            NodeEquals.Operation.GE -> Opcodes.IF_ICMPLE
                        }, labelIf
                    )
                }

                VirtualType.LONG -> {
                    visitInsn(Opcodes.LCMP)
                    visitJumpInsn(
                        when (node.operation) {
                            NodeEquals.Operation.EQ -> Opcodes.IFNE
                            NodeEquals.Operation.NE -> Opcodes.IFEQ
                            NodeEquals.Operation.LT -> Opcodes.IFGE
                            NodeEquals.Operation.LE -> Opcodes.IFGT
                            NodeEquals.Operation.GT -> Opcodes.IFLE
                            NodeEquals.Operation.GE -> Opcodes.IFLT
                        }, labelIf
                    )
                }

                VirtualType.FLOAT,
                VirtualType.DOUBLE -> {
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
        return Variable.tmp(node, VirtualType.BOOLEAN)
    }

    private fun insertA(node: MethodNode, operation: NodeEquals.Operation, offset: Int) =
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

    private fun insertB(node: MethodNode, operation: NodeEquals.Operation, label: Label) =
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