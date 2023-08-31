package ru.DmN.pht.example.bf.compiler.java.compilers

import org.objectweb.asm.Label
import org.objectweb.asm.Opcodes
import org.objectweb.asm.tree.InsnNode
import org.objectweb.asm.tree.LdcInsnNode
import ru.DmN.pht.base.Compiler
import ru.DmN.pht.base.compiler.java.compilers.INodeCompiler
import ru.DmN.pht.base.compiler.java.ctx.CompilationContext
import ru.DmN.pht.base.parser.ast.NodeNodesList
import ru.DmN.pht.base.utils.Variable
import ru.DmN.pht.std.base.compiler.java.utils.body
import ru.DmN.pht.std.base.compiler.java.utils.computeName
import ru.DmN.pht.std.base.compiler.java.utils.method
import java.util.*

object NCBF : INodeCompiler<NodeNodesList> {
    override fun compile(node: NodeNodesList, compiler: Compiler, ctx: CompilationContext, ret: Boolean): Variable? {
        val mctx = ctx.method
        val bctx = ctx.body
        mctx.node.run {
            val label = Label()
            visitLabel(label)
            val index = mctx.createVariable(bctx, "bf\$index", "int", label).id
            val array = mctx.createVariable(bctx, "bf\$array", "[I", label).id
            val size = InsnNode(Opcodes.NOP)
            instructions.add(size)
            visitIntInsn(Opcodes.NEWARRAY, Opcodes.T_INT)
            visitVarInsn(Opcodes.ASTORE, array)
            visitInsn(Opcodes.ICONST_0)
            visitVarInsn(Opcodes.ISTORE, index)
            var lastIndex = 0
            var maxIndex = 0
            val stack: Stack<Pair<Label, Label>> = Stack()
            ctx.method.node.run {
                compiler.computeName(node.nodes.first(), ctx).forEach {
                    when (it) {
                        '>' -> {
                            lastIndex++
                            if (lastIndex > maxIndex)
                                maxIndex = lastIndex
                            visitIincInsn(index, 1)
                        }
                        '<' -> {
                            lastIndex--
                            visitIincInsn(index, -1)
                        }
                        '+', '-' -> {
                            visitVarInsn(Opcodes.ALOAD, array)
                            visitVarInsn(Opcodes.ILOAD, index)
                            visitInsn(Opcodes.DUP2)
                            visitInsn(Opcodes.IALOAD)
                            visitInsn(Opcodes.ICONST_1)
                            visitInsn(if (it == '+') Opcodes.IADD else Opcodes.ISUB)
                            visitInsn(Opcodes.IASTORE)
                        }
                        '.' -> {
                            visitFieldInsn(Opcodes.GETSTATIC, "java/lang/System", "out", "Ljava/io/PrintStream;")
                            visitVarInsn(Opcodes.ALOAD, array)
                            visitVarInsn(Opcodes.ILOAD, index)
                            visitInsn(Opcodes.IALOAD)
                            visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/io/PrintStream", "print", "(C)V", false)
                        }
                        '[' -> {
                            val start = Label()
                            val stop = Label()
                            visitLabel(start)
                            visitVarInsn(Opcodes.ALOAD, array)
                            visitVarInsn(Opcodes.ILOAD, index)
                            visitInsn(Opcodes.IALOAD)
                            visitJumpInsn(Opcodes.IFEQ, stop)
                            stack.push(Pair(start, stop))
                        }
                        ']' -> {
                            val pair = stack.pop()
                            visitJumpInsn(Opcodes.GOTO, pair.first)
                            visitLabel(pair.second)
                        }

                        else -> throw RuntimeException()
                    }
                }
            }
            instructions.set(size, LdcInsnNode(maxIndex + 1))
        }
        return null
    }
}