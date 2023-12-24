package ru.DmN.pht.std.compiler.java.compilers

import org.objectweb.asm.Opcodes
import ru.DmN.pht.std.compiler.java.utils.load
import ru.DmN.pht.std.compiler.java.utils.method
import ru.DmN.pht.std.node.NodeTypes.*
import ru.DmN.pht.std.utils.type
import ru.DmN.siberia.Compiler
import ru.DmN.siberia.ast.NodeNodesList
import ru.DmN.siberia.compiler.ctx.CompilationContext
import ru.DmN.siberia.compilers.INodeCompiler
import ru.DmN.siberia.utils.Variable

object NCMath : INodeCompiler<NodeNodesList> {
    override fun compileVal(node: NodeNodesList, compiler: Compiler, ctx: CompilationContext): Variable {
        ctx.method.node.run {
            val type = compiler.compileVal(node.nodes[0], ctx).apply { load(this, this@run) }.type()
            if (node.type == NEG_)
                visitInsn(calcOperation(node, type.name))
            else {
                val operation = calcOperation(node, type.name)
                node.nodes.drop(1).forEach {
                    load(compiler.compileVal(it, ctx), this)
                    visitInsn(operation)
                }
            }
            return Variable.tmp(node, type)
        }
    }

    private fun calcOperation(node: NodeNodesList, type: String) =
        when (node.type) {
            ADD_ -> Opcodes.IADD + calcOffsetMath(type)
            SUB_ -> Opcodes.ISUB + calcOffsetMath(type)
            MUL_ -> Opcodes.IMUL + calcOffsetMath(type)
            DIV_ -> Opcodes.IDIV + calcOffsetMath(type)
            REM_ -> Opcodes.IREM + calcOffsetMath(type)
            NEG_ -> Opcodes.INEG + calcOffsetMath(type)
            AND_ -> Opcodes.IAND + calcOffsetAndOr(type)
            OR_  -> Opcodes.IOR  + calcOffsetAndOr(type)
            XOR_ -> Opcodes.IXOR + calcOffsetXorShift(type)
            SHIFT_LEFT_  -> Opcodes.ISHL + calcOffsetAndOr(type)
            SHIFT_RIGHT_ -> Opcodes.ISHR + calcOffsetAndOr(type)
            else  -> throw RuntimeException()
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