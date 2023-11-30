package ru.DmN.pht.std.compiler.java.compilers

import org.objectweb.asm.Opcodes
import org.objectweb.asm.tree.ClassNode
import ru.DmN.pht.std.ast.NodeType
import ru.DmN.pht.std.compiler.java.ctx.ClassContext
import ru.DmN.pht.std.compiler.java.utils.method
import ru.DmN.pht.std.compiler.java.utils.with
import ru.DmN.siberia.Compiler
import ru.DmN.siberia.compiler.ctx.CompilationContext
import ru.DmN.siberia.compiler.utils.CompilingStage
import ru.DmN.siberia.compiler.utils.javaClassVersion
import ru.DmN.siberia.compilers.INodeCompiler
import ru.DmN.siberia.compilers.NCDefault
import ru.DmN.siberia.utils.Variable
import ru.DmN.siberia.utils.desc
import ru.DmN.siberia.utils.text

object NCClass : INodeCompiler<NodeType> {
    override fun compile(node: NodeType, compiler: Compiler, ctx: CompilationContext) {
        compiler.stageManager.pushTask(CompilingStage.TYPES_PREDEFINE) {
            val cn = ClassNode().apply {
                compiler.classes[node.type.name] = this
                visit(
                    ctx.javaClassVersion,
                    Opcodes.ACC_PUBLIC.let {
                        when (node.text) {
                            "!enum" -> it + Opcodes.ACC_ENUM
                            "!itf" -> it + Opcodes.ACC_INTERFACE + Opcodes.ACC_ABSTRACT
                            else ->
                                if (node.abstract) it + Opcodes.ACC_ABSTRACT
                                else if (node.open) it
                                else it + Opcodes.ACC_FINAL
                        }
                    },
                    node.type.className,
                    null,
                    node.type.superclass?.className ?: "java/lang/Object",
                    node.type.interfaces.map { it.className }.toTypedArray()
                )
            }
            compiler.stageManager.pushTask(CompilingStage.TYPES_DEFINE) {
                if (node.token.text == "!obj") {
                    cn.visitField(
                        Opcodes.ACC_PUBLIC + Opcodes.ACC_STATIC + Opcodes.ACC_FINAL,
                        "INSTANCE",
                        cn.name.desc,
                        null,
                        null
                    )
                    cn.visitMethod(Opcodes.ACC_STATIC, "<clinit>", "()V", null, null).run {
                        visitCode()
                        visitTypeInsn(Opcodes.NEW, cn.name)
                        visitInsn(Opcodes.DUP)
                        visitMethodInsn(Opcodes.INVOKESPECIAL, cn.name, "<init>", "()V", false)
                        visitFieldInsn(Opcodes.PUTSTATIC, cn.name, "INSTANCE", cn.name.desc)
                        visitInsn(Opcodes.RETURN)
                        visitEnd()
                    }
                }
                NCDefault.compile(node, compiler, ctx.with(ClassContext(cn, node.type)))
                if (node.text == "!obj") {
                    cn.methods.find { it.name == "<init>" && it.desc == "()V" } ?: cn.visitMethod(
                        Opcodes.ACC_PRIVATE,
                        "<init>",
                        "()V",
                        null,
                        null
                    ).run {
                        visitCode()
                        visitVarInsn(Opcodes.ALOAD, 0)
                        visitMethodInsn(Opcodes.INVOKESPECIAL, cn.superName, "<init>", "()V", false)
                        visitInsn(Opcodes.RETURN)
                        visitEnd()
                    }
                }
            }
        }
    }

    override fun compileVal(node: NodeType, compiler: Compiler, ctx: CompilationContext): Variable {
        compile(node, compiler, ctx)
        compiler.stageManager.pushTask(CompilingStage.METHODS_BODY) {
            ctx.method.node.visitFieldInsn(Opcodes.GETSTATIC, node.type.className, "INSTANCE", node.type.desc)
        }
        return Variable.tmp(node, node.type)
    }
}