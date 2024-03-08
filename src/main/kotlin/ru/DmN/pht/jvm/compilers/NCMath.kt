package ru.DmN.pht.compiler.java.compilers

import org.objectweb.asm.Opcodes
import ru.DmN.pht.ast.NodeMath
import ru.DmN.pht.compiler.java.utils.load
import ru.DmN.pht.jvm.compiler.ctx.method
import ru.DmN.pht.utils.node.NodeTypes.*
import ru.DmN.siberia.compiler.Compiler
import ru.DmN.siberia.compiler.ctx.CompilationContext
import ru.DmN.siberia.compilers.INodeCompiler
import ru.DmN.siberia.utils.Variable

object NCMath : INodeCompiler<NodeMath> {
    override fun compileVal(node: NodeMath, compiler: Compiler, ctx: CompilationContext): Variable {
        ctx.method.node.run {
            load(compiler.compileVal(node.nodes[0], ctx), this)
            if (node.info.type == NEG_)
                visitInsn(calcOperation(node, node.type.name))
            else {
                val operation = calcOperation(node, node.type.name)
                node.nodes.drop(1).forEach {
                    load(compiler.compileVal(it, ctx), this)
                    visitInsn(operation)
                }
            }
            return Variable.tmp(node, node.type)
        }
    }

    private fun calcOperation(node: NodeMath, type: String) =
        when (node.info.type) {
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