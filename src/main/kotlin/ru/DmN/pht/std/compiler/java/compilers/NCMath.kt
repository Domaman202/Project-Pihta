package ru.DmN.pht.std.compiler.java.compilers

import org.objectweb.asm.Opcodes
import ru.DmN.pht.base.ast.NodeNodesList
import ru.DmN.pht.base.compiler.java.Compiler
import ru.DmN.pht.base.compiler.java.compilers.INodeCompiler
import ru.DmN.pht.base.compiler.java.utils.CompilationContext
import ru.DmN.pht.base.utils.Variable
import ru.DmN.pht.base.utils.VirtualType
import ru.DmN.pht.std.compiler.java.utils.load
import ru.DmN.pht.std.compiler.java.utils.method

object NCMath : INodeCompiler<NodeNodesList> {
    override fun compileVal(node: NodeNodesList, compiler: Compiler, ctx: CompilationContext): Variable {
        ctx.method.node.run {
            val type = compiler.compileVal(node.nodes[0], ctx).apply { load(this, this@run) }.type()
            val operation = calcOperation(node, type.name)
            node.nodes.drop(1).forEach {
                load(compiler.compileVal(it, ctx), this)
                visitInsn(operation)
            }
            return Variable.tmp(node, type)
        }
    }

    private fun calcOperation(node: NodeNodesList, type: String) =
        when (node.token.text) {
            "!add" -> Opcodes.IADD + calcOffsetMath(type)
            "!sub" -> Opcodes.ISUB + calcOffsetMath(type)
            "!mul" -> Opcodes.IMUL + calcOffsetMath(type)
            "!div" -> Opcodes.IDIV + calcOffsetMath(type)
            "!rem" -> Opcodes.IREM + calcOffsetMath(type)
            "!neg" -> Opcodes.INEG + calcOffsetMath(type)
            "!and" -> Opcodes.IAND + calcOffsetAndOr(type)
            "!or"  -> Opcodes.IOR  + calcOffsetAndOr(type)
            "!xor" -> Opcodes.IXOR + calcOffsetXorShift(type)
            "!bit-shift-left"  -> Opcodes.ISHL + calcOffsetXorShift(type)
            "!bit-shift-right" -> Opcodes.ISHR + calcOffsetXorShift(type)
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