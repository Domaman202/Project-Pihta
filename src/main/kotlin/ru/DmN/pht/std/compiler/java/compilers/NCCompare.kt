package ru.DmN.pht.std.compiler.java.compilers

import org.objectweb.asm.Label
import org.objectweb.asm.Opcodes
import ru.DmN.pht.std.compiler.java.utils.load
import ru.DmN.pht.std.compiler.java.utils.method
import ru.DmN.pht.std.node.NodeTypes.*
import ru.DmN.pht.std.utils.type
import ru.DmN.siberia.Compiler
import ru.DmN.siberia.ast.Node
import ru.DmN.siberia.ast.NodeNodesList
import ru.DmN.siberia.compiler.ctx.CompilationContext
import ru.DmN.siberia.compilers.INodeCompiler
import ru.DmN.siberia.node.INodeType
import ru.DmN.siberia.utils.Variable
import ru.DmN.siberia.utils.VirtualType

object NCCompare : INodeCompiler<NodeNodesList> {
    override fun compileVal(node: NodeNodesList, compiler: Compiler, ctx: CompilationContext): Variable {
        insertIf(node.type, node.nodes, { ctx.method.node.visitInsn(Opcodes.ICONST_1) }, { ctx.method.node.visitInsn(Opcodes.ICONST_0) }, compiler, ctx)
        return Variable.tmp(node, VirtualType.BOOLEAN)
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
                    VirtualType.BOOLEAN -> visitInsn(Opcodes.INEG)
                    else -> throw RuntimeException()
                }
                EQ_ -> when (type) {
                    VirtualType.BOOLEAN,
                    VirtualType.BYTE,
                    VirtualType.SHORT,
                    VirtualType.CHAR,
                    VirtualType.INT -> visitJumpInsn(Opcodes.IF_ICMPEQ, ifLabel)
                    else -> throw RuntimeException()
                }
                NOT_EQ_ -> when (type) {
                    VirtualType.BOOLEAN,
                    VirtualType.BYTE,
                    VirtualType.SHORT,
                    VirtualType.CHAR,
                    VirtualType.INT -> visitJumpInsn(Opcodes.IF_ICMPNE, ifLabel)
                    else -> throw RuntimeException()
                }
                GREAT_ -> when (type) {
                    VirtualType.BOOLEAN,
                    VirtualType.BYTE,
                    VirtualType.SHORT,
                    VirtualType.CHAR,
                    VirtualType.INT -> visitJumpInsn(Opcodes.IF_ICMPGT, ifLabel)
                    else -> throw RuntimeException()
                }
                GREAT_OR_EQ_ -> when (type) {
                    VirtualType.BOOLEAN,
                    VirtualType.BYTE,
                    VirtualType.SHORT,
                    VirtualType.CHAR,
                    VirtualType.INT -> visitJumpInsn(Opcodes.IF_ICMPGE, ifLabel)
                    else -> throw RuntimeException()
                }
                LESS_ -> when (type) {
                    VirtualType.BOOLEAN,
                    VirtualType.BYTE,
                    VirtualType.SHORT,
                    VirtualType.CHAR,
                    VirtualType.INT -> visitJumpInsn(Opcodes.IF_ICMPLT, ifLabel)
                    else -> throw RuntimeException()
                }
                LESS_OR_EQ_ -> when (type) {
                    VirtualType.BOOLEAN,
                    VirtualType.BYTE,
                    VirtualType.SHORT,
                    VirtualType.CHAR,
                    VirtualType.INT -> visitJumpInsn(Opcodes.IF_ICMPLE, ifLabel)
                    else -> throw RuntimeException()
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