package ru.DmN.pht.std.compiler.java.compilers

import org.objectweb.asm.Label
import org.objectweb.asm.Opcodes
import ru.DmN.pht.base.ast.Node
import ru.DmN.pht.base.ast.NodeNodesList
import ru.DmN.pht.base.compiler.java.Compiler
import ru.DmN.pht.base.compiler.java.compilers.INodeCompiler
import ru.DmN.pht.base.compiler.java.utils.CompilationContext
import ru.DmN.pht.base.utils.Variable
import ru.DmN.pht.base.utils.VirtualType
import ru.DmN.pht.std.compiler.java.utils.load
import ru.DmN.pht.std.compiler.java.utils.method

object NCCompare : INodeCompiler<NodeNodesList> {
    override fun compileVal(node: NodeNodesList, compiler: Compiler, ctx: CompilationContext): Variable {
        insertIf(node.token.text!!, node.nodes, { ctx.method.node.visitInsn(Opcodes.ICONST_1) }, { ctx.method.node.visitInsn(Opcodes.ICONST_0) }, compiler, ctx)
        return Variable.tmp(node, VirtualType.BOOLEAN)
    }

    fun insertIf(operation: String, nodes: List<Node>, ifInsert: () -> Unit, elseInsert: () -> Unit, compiler: Compiler, ctx: CompilationContext) {
        ctx.method.node.run {
            val type = nodes
                .map { compiler.compileVal(it, ctx).apply { load(this, this@run) }.type }
                .first()
            val ifLabel = Label()
            val elseLabel = Label()
            val exitLabel = Label()
            when (operation) {
                "!not" -> when (type) {
                    VirtualType.BOOLEAN -> visitInsn(Opcodes.INEG)
                    else -> throw RuntimeException()
                }
                "!eq" -> when (type) {
                    VirtualType.BOOLEAN,
                    VirtualType.BYTE,
                    VirtualType.SHORT,
                    VirtualType.CHAR,
                    VirtualType.INT -> visitJumpInsn(Opcodes.IF_ICMPEQ, ifLabel)
                    else -> throw RuntimeException()
                }
                "!not-eq" -> when (type) {
                    VirtualType.BOOLEAN,
                    VirtualType.BYTE,
                    VirtualType.SHORT,
                    VirtualType.CHAR,
                    VirtualType.INT -> visitJumpInsn(Opcodes.IF_ICMPNE, ifLabel)
                    else -> throw RuntimeException()
                }
                "!great" -> when (type) {
                    VirtualType.BOOLEAN,
                    VirtualType.BYTE,
                    VirtualType.SHORT,
                    VirtualType.CHAR,
                    VirtualType.INT -> visitJumpInsn(Opcodes.IF_ICMPGT, ifLabel)
                    else -> throw RuntimeException()
                }
                "!great-or-eq" -> when (type) {
                    VirtualType.BOOLEAN,
                    VirtualType.BYTE,
                    VirtualType.SHORT,
                    VirtualType.CHAR,
                    VirtualType.INT -> visitJumpInsn(Opcodes.IF_ICMPGE, ifLabel)
                    else -> throw RuntimeException()
                }
                "!less" -> when (type) {
                    VirtualType.BOOLEAN,
                    VirtualType.BYTE,
                    VirtualType.SHORT,
                    VirtualType.CHAR,
                    VirtualType.INT -> visitJumpInsn(Opcodes.IF_ICMPLT, ifLabel)
                    else -> throw RuntimeException()
                }
                "!less-or-eq" -> when (type) {
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