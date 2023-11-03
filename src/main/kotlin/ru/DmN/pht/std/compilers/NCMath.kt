package ru.DmN.pht.std.compilers

import org.objectweb.asm.Opcodes
import ru.DmN.pht.base.ast.NodeNodesList
import ru.DmN.pht.base.compiler.java.Compiler
import ru.DmN.pht.base.compiler.java.compilers.INodeCompiler
import ru.DmN.pht.base.compiler.java.utils.CompilationContext
import ru.DmN.pht.base.utils.Variable
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
            "!add" -> Opcodes.IADD + calcOffset(type)
            "!sub" -> Opcodes.ISUB + calcOffset(type)
            "!mul" -> Opcodes.IMUL + calcOffset(type)
            "!div" -> Opcodes.IDIV + calcOffset(type)
            "!rem" -> Opcodes.IREM + calcOffset(type)
            else  -> throw RuntimeException()
        }

    private fun calcOffset(type: String) =
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