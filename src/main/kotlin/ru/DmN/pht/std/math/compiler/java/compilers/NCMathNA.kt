package ru.DmN.pht.std.math.compiler.java.compilers

import org.objectweb.asm.Opcodes
import ru.DmN.pht.base.compiler.java.Compiler
import ru.DmN.pht.base.compiler.java.ctx.CompilationContext
import ru.DmN.pht.base.utils.Variable
import ru.DmN.pht.base.utils.VirtualType
import ru.DmN.pht.std.base.compiler.java.compilers.IStdNodeCompiler
import ru.DmN.pht.std.base.compiler.java.utils.method
import ru.DmN.pht.std.base.utils.calcNumberType
import ru.DmN.pht.std.base.utils.load
import ru.DmN.pht.std.math.ast.NodeMathNA

object NCMathNA : IStdNodeCompiler<NodeMathNA> {
    override fun calc(node: NodeMathNA, compiler: Compiler, ctx: CompilationContext): VirtualType? = calcNumberType(
        compiler.calc(node.nodes.first(), ctx),
        compiler.calc(node.nodes.last(), ctx)
    )

    override fun compile(node: NodeMathNA, compiler: Compiler, ctx: CompilationContext, ret: Boolean): Variable? =
        ctx.method.node.run {
            val type = node.nodes.map { compiler.calc(node, ctx) }.reduce { a, b -> calcNumberType(a, b) }?.name ?: "int"
            val operation = calcOperation(node, type)
            compiler.compile(node.nodes.first(), ctx, true)?.let { load(it, this) }
            for (i in 1 until node.nodes.size) {
                compiler.compile(node.nodes[i], ctx, true)?.let { load(it, this) }
                visitInsn(operation)
            }
            Variable("tmp$${node.hashCode()}", type, -1, true)
        }

    private fun calcOperation(node: NodeMathNA, type: String) =
        when (node.operation) {
            NodeMathNA.Operation.ADD -> Opcodes.IADD + calcOffset(type)
            NodeMathNA.Operation.SUB -> Opcodes.ISUB + calcOffset(type)
            NodeMathNA.Operation.MUL -> Opcodes.IMUL + calcOffset(type)
            NodeMathNA.Operation.DIV -> Opcodes.IDIV + calcOffset(type)
        }

    private fun calcOffset(type: String) =
        when (type) {
            "byte", "short", "int" -> 0
            "long" -> 1
            "float" -> 2
            "double" -> 3
            else -> throw RuntimeException()
        }
}