package ru.DmN.pht.std.math.compiler.java.compilers

import org.objectweb.asm.Opcodes
import ru.DmN.pht.base.compiler.java.Compiler
import ru.DmN.pht.base.compiler.java.compilers.NodeCompiler
import ru.DmN.pht.base.compiler.java.ctx.CompilationContext
import ru.DmN.pht.base.utils.Variable
import ru.DmN.pht.base.utils.VirtualType
import ru.DmN.pht.std.utils.calcNumberType
import ru.DmN.pht.std.utils.load
import ru.DmN.pht.std.math.ast.NodeMathNA

object NCMathNA : NodeCompiler<NodeMathNA>() { // todo: list
    override fun calc(node: NodeMathNA, compiler: Compiler, ctx: CompilationContext): VirtualType? = calcNumberType(
        compiler.calc(node.nodes.first(), ctx),
        compiler.calc(node.nodes.last(), ctx)
    )

    override fun compile(node: NodeMathNA, compiler: Compiler, ctx: CompilationContext, ret: Boolean): Variable? =
        if (ctx.type.method)
            ctx.method!!.node.run {
                val valueA = compiler.compile(node.nodes.first(), ctx, true)!!.apply { load(this, this@run) }
                val valueB = compiler.compile(node.nodes.last(), ctx, true)!!.apply { load(this, this@run) }
                val rettype = calcNumberType(
                    valueA.type?.let { ctx.global.getType(compiler, it) },
                    valueB.type?.let { ctx.global.getType(compiler, it) }
                )!!.name
                visitInsn(
                    when (node.operation) {
                        NodeMathNA.Operation.ADD -> {
                            when (rettype) {
                                "byte", "short", "int" -> Opcodes.IADD
                                "long" -> Opcodes.LADD
                                "float" -> Opcodes.FADD
                                "double" -> Opcodes.DADD
                                else -> throw Error()
                            }
                        }

                        NodeMathNA.Operation.SUB -> {
                            when (rettype) {
                                "byte", "short", "int" -> Opcodes.ISUB
                                "long" -> Opcodes.LSUB
                                "float" -> Opcodes.FSUB
                                "double" -> Opcodes.DSUB
                                else -> throw Error()
                            }
                        }

                        NodeMathNA.Operation.MUL -> {
                            when (rettype) {
                                "byte", "short", "int" -> Opcodes.IMUL
                                "long" -> Opcodes.LMUL
                                "float" -> Opcodes.FMUL
                                "double" -> Opcodes.DMUL
                                else -> throw Error()
                            }
                        }

                        NodeMathNA.Operation.DIV -> {
                            when (rettype) {
                                "byte", "short", "int" -> Opcodes.IDIV
                                "long" -> Opcodes.LDIV
                                "float" -> Opcodes.FDIV
                                "double" -> Opcodes.DDIV
                                else -> throw Error()
                            }
                        }
                    }
                )
                Variable("tmp$${node.hashCode()}", rettype, -1, true)
            }
        else null
}