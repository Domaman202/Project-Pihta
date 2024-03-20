package ru.DmN.pht.compiler.java.compilers

import org.objectweb.asm.Opcodes
import ru.DmN.pht.ast.NodeIncDec
import ru.DmN.pht.jvm.compiler.ctx.body
import ru.DmN.pht.jvm.compiler.ctx.clazz
import ru.DmN.pht.jvm.compiler.ctx.method
import ru.DmN.pht.jvm.utils.vtype.desc
import ru.DmN.pht.jvm.utils.vtype.jvmName
import ru.DmN.pht.utils.node.NodeTypes.*
import ru.DmN.siberia.compiler.Compiler
import ru.DmN.siberia.compiler.ctx.CompilationContext
import ru.DmN.siberia.compilers.INodeCompiler
import ru.DmN.siberia.utils.Variable
import ru.DmN.siberia.utils.vtype.VirtualType

object NCIncDec : INodeCompiler<NodeIncDec> {
    override fun compile(node: NodeIncDec, compiler: Compiler, ctx: CompilationContext) {
        ctx.method.node.run {
            val operation = node.info.type
            val variable = ctx.body[node.name]
            if (variable == null) {
                val clazz = ctx.clazz.clazz
                val field = clazz.fields.find { it.name == node.name }!!
                if (field.modifiers.isStatic)
                    visitFieldInsn(Opcodes.GETSTATIC, clazz.jvmName, field.name, field.desc)
                else {
                    visitVarInsn(Opcodes.ALOAD, 0)
                    visitFieldInsn(Opcodes.GETFIELD, clazz.jvmName, field.name, field.desc)
                }
                when (field.type) {
                    VirtualType.BOOLEAN,
                    VirtualType.BYTE,
                    VirtualType.SHORT,
                    VirtualType.CHAR,
                    VirtualType.INT -> {
                        visitLdcInsn(when (operation) {
                            INC_PRE_, INC_POST_ -> 1
                            DEC_PRE_, DEC_POST_ -> -1
                            else -> throw RuntimeException()
                        })
                        visitInsn(Opcodes.IADD)
                    }
                    VirtualType.LONG -> {
                        visitLdcInsn(when (operation) {
                            INC_PRE_, INC_POST_ -> 1L
                            DEC_PRE_, DEC_POST_ -> -1L
                            else -> throw RuntimeException()
                        })
                        visitInsn(Opcodes.LADD)
                    }
                    VirtualType.FLOAT -> {
                        visitLdcInsn(when (operation) {
                            INC_PRE_, INC_POST_ -> 1f
                            DEC_PRE_, DEC_POST_ -> -1f
                            else -> throw RuntimeException()
                        })
                        visitInsn(Opcodes.FADD)
                    }
                    VirtualType.DOUBLE -> {
                        visitLdcInsn(when (operation) {
                            INC_PRE_, INC_POST_ -> 1.0
                            DEC_PRE_, DEC_POST_ -> -1.0
                            else -> throw RuntimeException()
                        })
                        visitInsn(Opcodes.DADD)
                    }
                    else -> throw UnsupportedOperationException()
                }
                if (field.modifiers.isStatic)
                    visitFieldInsn(Opcodes.PUTSTATIC, clazz.jvmName, field.name, field.desc)
                else {
                    visitVarInsn(Opcodes.ALOAD, 0)
                    visitInsn(Opcodes.SWAP)
                    visitFieldInsn(Opcodes.PUTFIELD, clazz.jvmName, field.name, field.desc)
                }
            } else {
                visitIincInsn(
                    variable.id,
                    when (operation) {
                        INC_PRE_, INC_POST_ -> 1
                        DEC_PRE_, DEC_POST_ -> -1
                        else -> throw RuntimeException()
                    }
                )
            }
        }
    }

    override fun compileVal(node: NodeIncDec, compiler: Compiler, ctx: CompilationContext): Variable {
        ctx.method.node.run {
            val operation = node.info.type
            val variable = ctx.body[node.name]
            if (variable == null) {
                val clazz = ctx.clazz.clazz
                val field = clazz.fields.find { it.name == node.name }!!
                if (field.modifiers.isStatic)
                    visitFieldInsn(Opcodes.GETSTATIC, clazz.jvmName, field.name, field.desc)
                else {
                    visitVarInsn(Opcodes.ALOAD, 0)
                    visitFieldInsn(Opcodes.GETFIELD, clazz.jvmName, field.name, field.desc)
                }
                if (operation == INC_POST_ || operation == DEC_POST_)
                    visitInsn(Opcodes.DUP)
                when (field.type) {
                    VirtualType.BOOLEAN,
                    VirtualType.BYTE,
                    VirtualType.SHORT,
                    VirtualType.CHAR,
                    VirtualType.INT -> {
                        visitLdcInsn(when (operation) {
                            INC_PRE_, INC_POST_ -> 1
                            DEC_PRE_, DEC_POST_ -> -1
                            else -> throw RuntimeException()
                        })
                        visitInsn(Opcodes.IADD)
                    }
                    VirtualType.LONG -> {
                        visitLdcInsn(when (operation) {
                            INC_PRE_, INC_POST_ -> 1L
                            DEC_PRE_, DEC_POST_ -> -1L
                            else -> throw RuntimeException()
                        })
                        visitInsn(Opcodes.LADD)
                    }
                    VirtualType.FLOAT -> {
                        visitLdcInsn(when (operation) {
                            INC_PRE_, INC_POST_ -> 1f
                            DEC_PRE_, DEC_POST_ -> -1f
                            else -> throw RuntimeException()
                        })
                        visitInsn(Opcodes.FADD)
                    }
                    VirtualType.DOUBLE -> {
                        visitLdcInsn(when (operation) {
                            INC_PRE_, INC_POST_ -> 1.0
                            DEC_PRE_, DEC_POST_ -> -1.0
                            else -> throw RuntimeException()
                        })
                        visitInsn(Opcodes.DADD)
                    }
                    else -> throw UnsupportedOperationException()
                }
                if (operation == INC_PRE_ || operation == DEC_PRE_)
                    visitInsn(Opcodes.DUP)
                if (field.modifiers.isStatic)
                    visitFieldInsn(Opcodes.PUTSTATIC, clazz.jvmName, field.name, field.desc)
                else {
                    visitVarInsn(Opcodes.ALOAD, 0)
                    visitInsn(Opcodes.SWAP)
                    visitFieldInsn(Opcodes.PUTFIELD, clazz.jvmName, field.name, field.desc)
                }
            } else {
                val id = variable.id
                if (operation == INC_POST_ || operation == DEC_POST_)
                    visitVarInsn(Opcodes.ILOAD, id)
                visitIincInsn(
                    id,
                    when (operation) {
                        INC_PRE_, INC_POST_ -> 1
                        DEC_PRE_, DEC_POST_ -> -1
                        else -> throw RuntimeException()
                    }
                )
                if (operation == INC_PRE_ || operation == DEC_PRE_) {
                    visitVarInsn(Opcodes.ILOAD, id)
                }
            }
        }
        return Variable.tmp(node, VirtualType.INT)
    }
}