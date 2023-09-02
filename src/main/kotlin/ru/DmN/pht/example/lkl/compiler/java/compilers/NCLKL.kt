package ru.DmN.pht.example.lkl.compiler.java.compilers

import org.objectweb.asm.Opcodes
import ru.DmN.pht.base.compiler.java.Compiler
import ru.DmN.pht.base.compiler.java.compilers.INodeCompiler
import ru.DmN.pht.base.compiler.java.ctx.CompilationContext
import ru.DmN.pht.base.parser.ast.Node
import ru.DmN.pht.base.utils.Variable
import ru.DmN.pht.std.base.compiler.java.utils.method

object NCLKL : INodeCompiler<Node> {
    override fun compile(node: Node, compiler: Compiler, ctx: CompilationContext, ret: Boolean): Variable? {
        val text = node.tkOperation.text!!
        if (text.isNotEmpty()) {
            ctx.method.node.run {
                visitFieldInsn(Opcodes.GETSTATIC, "java/lang/System", "out", "Ljava/io/PrintStream;")
                text.forEachIndexed { i, it ->
                    if (i + 1 < text.length)
                        visitInsn(Opcodes.DUP)
                    visitLdcInsn(
                        when (it) {
                            'l' -> "LOL!"
                            'k' -> "KEK!"
                            else -> throw RuntimeException()
                        }
                    )
                    visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/io/PrintStream", "println", "(Ljava/lang/String;)V", false)
                }
            }
        }
        return null
    }
}