package ru.DmN.pht.std.compilers

import org.objectweb.asm.Opcodes
import ru.DmN.pht.base.compiler.java.Compiler
import ru.DmN.pht.base.compiler.java.compilers.INodeCompiler
import ru.DmN.pht.base.compiler.java.utils.CompilationContext
import ru.DmN.pht.base.utils.Variable
import ru.DmN.pht.std.ast.NodeMath
import ru.DmN.pht.std.compiler.java.utils.load
import ru.DmN.pht.std.compiler.java.utils.method

object NCMath : INodeCompiler<NodeMath> {
    override fun compileVal(node: NodeMath, compiler: Compiler, ctx: CompilationContext): Variable {
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

    private fun calcOperation(node: NodeMath, type: String) =
        when (node.operation) {
            NodeMath.Operation.PLUS     -> Opcodes.IADD + calcOffset(type)
            NodeMath.Operation.MINUS    -> Opcodes.ISUB + calcOffset(type)
            NodeMath.Operation.MUL      -> Opcodes.IMUL + calcOffset(type)
            NodeMath.Operation.DIV      -> Opcodes.IDIV + calcOffset(type)
        }

    private fun calcOffset(type: String) =
        when (type) {
            "boolean",
            "byte",
            "short",
            "int" -> 0
            "long" -> 1
            "float" -> 2
            "double" -> 3
            else -> throw RuntimeException()
        }
}