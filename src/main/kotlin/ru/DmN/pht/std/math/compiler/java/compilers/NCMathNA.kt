package ru.DmN.pht.std.math.compiler.java.compilers

import org.objectweb.asm.Opcodes
import ru.DmN.pht.base.compiler.java.Compiler
import ru.DmN.pht.base.compiler.java.ctx.CompilationContext
import ru.DmN.pht.base.utils.Variable
import ru.DmN.pht.base.utils.VirtualType
import ru.DmN.pht.std.compiler.java.compilers.IStdNodeCompiler
import ru.DmN.pht.std.compiler.java.global
import ru.DmN.pht.std.compiler.java.method
import ru.DmN.pht.std.math.ast.NodeMathNA
import ru.DmN.pht.std.utils.calcNumberType
import ru.DmN.pht.std.utils.load

object NCMathNA : IStdNodeCompiler<NodeMathNA> {
    override fun calc(node: NodeMathNA, compiler: Compiler, ctx: CompilationContext): VirtualType? = calcNumberType(
        compiler.calc(node.nodes.first(), ctx),
        compiler.calc(node.nodes.last(), ctx)
    )

    override fun compile(node: NodeMathNA, compiler: Compiler, ctx: CompilationContext, ret: Boolean): Variable? =
        ctx.method.node.run {
            val types = node.nodes
                .map { compiler.compile(it, ctx, true)!!.apply { load(this, this@run) } }
                .map { it -> it.type?.let { ctx.global.getType(compiler, it) } }
            val type = types
                .reduce { a, b -> calcNumberType(a, b) }?.name ?: "int"
            val offset = when (type) {
                "byte", "short", "int" -> 0
                "long" -> 1
                "float" -> 2
                "double" -> 3
                else -> throw RuntimeException()
            }
            for (i in 1 until types.size) {
                visitInsn(
                    when (node.operation) {
                        NodeMathNA.Operation.ADD -> Opcodes.IADD + offset
                        NodeMathNA.Operation.SUB -> Opcodes.ISUB + offset
                        NodeMathNA.Operation.MUL -> Opcodes.IMUL + offset
                        NodeMathNA.Operation.DIV -> Opcodes.IDIV + offset
                    }
                )
            }
            Variable("tmp$${node.hashCode()}", type, -1, true)
        }
}