package ru.DmN.pht.compiler.java.compilers

import org.objectweb.asm.Label
import org.objectweb.asm.Opcodes
import ru.DmN.pht.compiler.java.utils.load
import ru.DmN.pht.jvm.compiler.ctx.method
import ru.DmN.pht.utils.node.NodeTypes.*
import ru.DmN.pht.utils.type
import ru.DmN.siberia.ast.Node
import ru.DmN.siberia.ast.NodeNodesList
import ru.DmN.siberia.compiler.Compiler
import ru.DmN.siberia.compiler.ctx.CompilationContext
import ru.DmN.siberia.compilers.INodeCompiler
import ru.DmN.siberia.utils.Variable
import ru.DmN.siberia.utils.node.INodeType
import ru.DmN.siberia.utils.vtype.VirtualType.Companion.BOOLEAN
import ru.DmN.siberia.utils.vtype.VirtualType.Companion.BYTE
import ru.DmN.siberia.utils.vtype.VirtualType.Companion.CHAR
import ru.DmN.siberia.utils.vtype.VirtualType.Companion.DOUBLE
import ru.DmN.siberia.utils.vtype.VirtualType.Companion.FLOAT
import ru.DmN.siberia.utils.vtype.VirtualType.Companion.INT
import ru.DmN.siberia.utils.vtype.VirtualType.Companion.SHORT

object NCCompare : INodeCompiler<NodeNodesList> {
    override fun compileVal(node: NodeNodesList, compiler: Compiler, ctx: CompilationContext): Variable {
        insertIf(node.type, node.nodes, { ctx.method.node.visitInsn(Opcodes.ICONST_1) }, { ctx.method.node.visitInsn(Opcodes.ICONST_0) }, compiler, ctx)
        return Variable.tmp(node, BOOLEAN)
    }

    fun insertIf(operation: INodeType, nodes: List<Node>, ifInsert: () -> Unit, elseInsert: () -> Unit, compiler: Compiler, ctx: CompilationContext) {
        ctx.method.node.run {
            val type = nodes
                .map { compiler.compileVal(it, ctx).apply { load(this, this@run) }.type }
                .first()
            val ifLabel = Label()
            val elseLabel = Label()
            val exitLabel = Label()
            when (operation) {
                NOT_ -> when (type) {
                    BOOLEAN -> visitInsn(Opcodes.INEG)
                    else -> throw UnsupportedOperationException()
                }
                EQ_ -> when (type) {
                    BOOLEAN,
                    BYTE,
                    SHORT,
                    CHAR,
                    INT    -> visitJumpInsn(Opcodes.IF_ICMPEQ, ifLabel)
                    FLOAT,
                    DOUBLE -> throw UnsupportedOperationException() // todo:
                    else   -> visitJumpInsn(Opcodes.IF_ACMPEQ, ifLabel)
                }
                NOT_EQ_ -> when (type) {
                    BOOLEAN,
                    BYTE,
                    SHORT,
                    CHAR,
                    INT    -> visitJumpInsn(Opcodes.IF_ICMPNE, ifLabel)
                    FLOAT,
                    DOUBLE -> throw UnsupportedOperationException() // todo:
                    else   -> visitJumpInsn(Opcodes.IF_ACMPNE, ifLabel)
                }
                GREAT_ -> when (type) {
                    BOOLEAN,
                    BYTE,
                    SHORT,
                    CHAR,
                    INT    -> visitJumpInsn(Opcodes.IF_ICMPGT, ifLabel)
                    FLOAT,
                    DOUBLE -> throw UnsupportedOperationException() // todo:
                    else   -> throw RuntimeException()
                }
                GREAT_OR_EQ_ -> when (type) {
                    BOOLEAN,
                    BYTE,
                    SHORT,
                    CHAR,
                    INT    -> visitJumpInsn(Opcodes.IF_ICMPGE, ifLabel)
                    FLOAT,
                    DOUBLE -> throw UnsupportedOperationException() // todo:
                    else   -> throw RuntimeException()
                }
                LESS_ -> when (type) {
                    BOOLEAN,
                    BYTE,
                    SHORT,
                    CHAR,
                    INT    -> visitJumpInsn(Opcodes.IF_ICMPLT, ifLabel)
                    FLOAT,
                    DOUBLE -> throw UnsupportedOperationException() // todo:
                    else   -> throw RuntimeException()
                }
                LESS_OR_EQ_ -> when (type) {
                    BOOLEAN,
                    BYTE,
                    SHORT,
                    CHAR,
                    INT    -> visitJumpInsn(Opcodes.IF_ICMPLE, ifLabel)
                    FLOAT,
                    DOUBLE -> throw UnsupportedOperationException() // todo:
                    else   -> throw RuntimeException()
                }
                else -> throw RuntimeException("Unknown operation \"$operation\"!")
            }
            visitLabel(elseLabel)
            elseInsert()
            visitJumpInsn(Opcodes.GOTO, exitLabel)
            visitLabel(ifLabel)
            ifInsert()
            visitLabel(exitLabel)
        }
    }
}