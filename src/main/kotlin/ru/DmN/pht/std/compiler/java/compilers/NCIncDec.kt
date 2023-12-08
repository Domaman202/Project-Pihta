package ru.DmN.pht.std.compiler.java.compilers

import org.objectweb.asm.Opcodes
import ru.DmN.siberia.Compiler
import ru.DmN.siberia.compilers.INodeCompiler
import ru.DmN.siberia.compiler.ctx.CompilationContext
import ru.DmN.siberia.utils.Variable
import ru.DmN.siberia.utils.VirtualType
import ru.DmN.pht.std.ast.NodeIncDec
import ru.DmN.pht.std.compiler.java.utils.body
import ru.DmN.pht.std.compiler.java.utils.clazz
import ru.DmN.pht.std.compiler.java.utils.method

object NCIncDec : INodeCompiler<NodeIncDec> {
    override fun compile(node: NodeIncDec, compiler: Compiler, ctx: CompilationContext) {
        ctx.method.node.run {
            val variable = ctx.body[node.name]
            if (variable == null) {
                val clazz = ctx.clazz.clazz
                val field = clazz.fields.find { it.name == node.name }!!
                if (field.isStatic)
                    visitFieldInsn(Opcodes.GETSTATIC, clazz.className, field.name, field.desc)
                else {
                    visitVarInsn(Opcodes.ALOAD, 0)
                    visitFieldInsn(Opcodes.GETFIELD, clazz.className, field.name, field.desc)
                }
                val operation = node.token.text!!
                when (field.type) {
                    VirtualType.BOOLEAN,
                    VirtualType.BYTE,
                    VirtualType.SHORT,
                    VirtualType.CHAR,
                    VirtualType.INT -> {
                        visitLdcInsn(when (operation) {
                            "!inc" -> 1
                            "!dec" -> -1
                            else -> throw RuntimeException()
                        })
                        visitInsn(Opcodes.IADD)
                    }
                    VirtualType.LONG -> {
                        visitLdcInsn(when (operation) {
                            "!inc" -> 1L
                            "!dec" -> -1L
                            else -> throw RuntimeException()
                        })
                        visitInsn(Opcodes.LADD)
                    }
                    VirtualType.FLOAT -> {
                        visitLdcInsn(when (operation) {
                            "!inc" -> 1f
                            "!dec" -> -1f
                            else -> throw RuntimeException()
                        })
                        visitInsn(Opcodes.FADD)
                    }
                    VirtualType.DOUBLE -> {
                        visitLdcInsn(when (operation) {
                            "!inc" -> 1.0
                            "!dec" -> -1.0
                            else -> throw RuntimeException()
                        })
                        visitInsn(Opcodes.DADD)
                    }
                    else -> throw UnsupportedOperationException()
                }
                if (field.isStatic)
                    visitFieldInsn(Opcodes.PUTSTATIC, clazz.className, field.name, field.desc)
                else {
                    visitVarInsn(Opcodes.ALOAD, 0)
                    visitInsn(Opcodes.SWAP)
                    visitFieldInsn(Opcodes.PUTFIELD, clazz.className, field.name, field.desc)
                }
            } else {
                visitIincInsn(variable.id, when (node.token.text) {
                        "!inc" -> 1
                        "!dec" -> -1
                        else -> throw RuntimeException()
                    }
                )
            }
        }
    }

    override fun compileVal(node: NodeIncDec, compiler: Compiler, ctx: CompilationContext): Variable {
        ctx.method.node.run {
            val variable = ctx.body[node.name]
            if (variable == null) {
                val clazz = ctx.clazz.clazz
                val field = clazz.fields.find { it.name == node.name }!!
                if (field.isStatic)
                    visitFieldInsn(Opcodes.GETSTATIC, clazz.className, field.name, field.desc)
                else {
                    visitVarInsn(Opcodes.ALOAD, 0)
                    visitFieldInsn(Opcodes.GETFIELD, clazz.className, field.name, field.desc)
                }
                val operation = node.token.text!!
                if (node.postfix)
                    visitInsn(Opcodes.DUP)
                when (field.type) {
                    VirtualType.BOOLEAN,
                    VirtualType.BYTE,
                    VirtualType.SHORT,
                    VirtualType.CHAR,
                    VirtualType.INT -> {
                        visitLdcInsn(when (operation) {
                            "!inc" -> 1
                            "!dec" -> -1
                            else -> throw RuntimeException()
                        })
                        visitInsn(Opcodes.IADD)
                    }
                    VirtualType.LONG -> {
                        visitLdcInsn(when (operation) {
                            "!inc" -> 1L
                            "!dec" -> -1L
                            else -> throw RuntimeException()
                        })
                        visitInsn(Opcodes.LADD)
                    }
                    VirtualType.FLOAT -> {
                        visitLdcInsn(when (operation) {
                            "!inc" -> 1f
                            "!dec" -> -1f
                            else -> throw RuntimeException()
                        })
                        visitInsn(Opcodes.FADD)
                    }
                    VirtualType.DOUBLE -> {
                        visitLdcInsn(when (operation) {
                            "!inc" -> 1.0
                            "!dec" -> -1.0
                            else -> throw RuntimeException()
                        })
                        visitInsn(Opcodes.DADD)
                    }
                    else -> throw UnsupportedOperationException()
                }
                if (!node.postfix)
                    visitInsn(Opcodes.DUP)
                if (field.isStatic)
                    visitFieldInsn(Opcodes.PUTSTATIC, clazz.className, field.name, field.desc)
                else {
                    visitVarInsn(Opcodes.ALOAD, 0)
                    visitInsn(Opcodes.SWAP)
                    visitFieldInsn(Opcodes.PUTFIELD, clazz.className, field.name, field.desc)
                }
            } else {
                val id = variable.id
                if (node.postfix)
                    visitVarInsn(Opcodes.ILOAD, id)
                visitIincInsn(id, when (node.token.text) {
                        "inc" -> 1
                        "dec" -> -1
                        else -> throw RuntimeException()
                    }
                )
                if (!node.postfix) {
                    visitVarInsn(Opcodes.ILOAD, id)
                }
            }
        }
        return Variable.tmp(node, VirtualType.INT)
    }
}