package ru.DmN.pht.compiler.java.compilers

import org.objectweb.asm.Label
import org.objectweb.asm.Opcodes
import ru.DmN.pht.ast.NodeMath
import ru.DmN.pht.compiler.java.utils.load
import ru.DmN.pht.jvm.compiler.ctx.method
import ru.DmN.pht.jvm.compilers.IValueNodeCompiler
import ru.DmN.pht.utils.node.NodeTypes.*
import ru.DmN.pht.utils.type
import ru.DmN.siberia.ast.NodeNodesList
import ru.DmN.siberia.compiler.Compiler
import ru.DmN.siberia.compiler.ctx.CompilationContext
import ru.DmN.siberia.utils.Variable
import ru.DmN.siberia.utils.node.INodeType
import ru.DmN.siberia.utils.vtype.VirtualType

object NCMath : IValueNodeCompiler<NodeMath> {
    override fun compileVal(node: NodeMath, compiler: Compiler, ctx: CompilationContext): Variable =
        compileVal(node, node.type, compiler, ctx)

    fun compileVal(node: NodeNodesList, type: VirtualType, compiler: Compiler, ctx: CompilationContext): Variable {
        ctx.method.node.run {
            load(compiler.compileVal(node.nodes[0], ctx), this)
            when (val nt = node.type) {
                NEG_ -> visitInsn(Opcodes.INEG + calcOffsetMath(type.name))
                NOT_ -> {
                    val labelIf = Label()
                    val labelExit = Label()
                    visitJumpInsn(Opcodes.IFNE, labelIf)
                    visitLdcInsn(true)
                    visitJumpInsn(Opcodes.GOTO, labelExit)
                    visitLabel(labelIf)
                    visitLdcInsn(false)
                    visitLabel(labelExit)
                }
                else -> {
                    val operation = calcOperation(type.name, nt)
                    node.nodes.drop(1).forEach {
                        load(compiler.compileVal(it, ctx), this)
                        visitInsn(operation)
                    }
                }
            }
            return Variable.tmp(node, type)
        }
    }

    private fun calcOperation(type: String, nt: INodeType) =
        when (nt) {
            ADD_         -> Opcodes.IADD + calcOffsetMath(type)
            SUB_         -> Opcodes.ISUB + calcOffsetMath(type)
            MUL_         -> Opcodes.IMUL + calcOffsetMath(type)
            DIV_         -> Opcodes.IDIV + calcOffsetMath(type)
            REM_         -> Opcodes.IREM + calcOffsetMath(type)
            AND_         -> Opcodes.IAND + calcOffsetAndOr(type)
            OR_          -> Opcodes.IOR  + calcOffsetAndOr(type)
            XOR_         -> Opcodes.IXOR + calcOffsetXorShift(type)
            SHIFT_LEFT_  -> Opcodes.ISHL + calcOffsetAndOr(type)
            SHIFT_RIGHT_ -> Opcodes.ISHR + calcOffsetAndOr(type)
            else         -> throw RuntimeException()
        }

    private fun calcOffsetXorShift(type: String) =
        when (type) {
            "boolean",
            "byte",
            "short",
            "int"   -> 0
            else    -> throw RuntimeException()
        }

    private fun calcOffsetAndOr(type: String) =
        when (type) {
            "boolean",
            "byte",
            "short",
            "int"   -> 0
            "long"  -> 1
            else    -> throw RuntimeException()
        }

    private fun calcOffsetMath(type: String) =
        when (type) {
            "boolean",
            "byte",
            "short",
            "int"   -> 0
            "long"  -> 1
            "float" -> 2
            "double"-> 3
            else -> throw RuntimeException()
        }
}