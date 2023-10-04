package ru.DmN.pht.std.oop.compiler.java.compilers

import org.objectweb.asm.Opcodes
import org.objectweb.asm.tree.ClassNode
import ru.DmN.pht.base.compiler.java.Compiler
import ru.DmN.pht.base.compiler.java.compilers.INodeCompiler
import ru.DmN.pht.base.compiler.java.compilers.NCDefault
import ru.DmN.pht.base.compiler.java.utils.CompilationContext
import ru.DmN.pht.base.compiler.java.utils.CompilingStage
import ru.DmN.pht.base.utils.desc
import ru.DmN.pht.std.base.compiler.java.ctx.ClassContext
import ru.DmN.pht.std.base.compiler.java.utils.with
import ru.DmN.pht.std.oop.ast.NodeType

object NCClass : INodeCompiler<NodeType> {
    override fun compile(node: NodeType, compiler: Compiler, ctx: CompilationContext) {
        compiler.pushTask(ctx, CompilingStage.TYPES_PREDEFINE) { // todo: itf / obj
            val cn = ClassNode().apply {
                compiler.classes += this
                visit(
                    Opcodes.V20,
                    Opcodes.ACC_PUBLIC.let {
                        if (node.abstract) it + Opcodes.ACC_ABSTRACT
                        else if (node.open) it
                        else it + Opcodes.ACC_FINAL
                    },
                    node.type.className,
                    null,
                    node.type.superclass?.className ?: "java/lang/Object",
                    node.type.interfaces.map { it.className }.toTypedArray()
                )
            }
            compiler.pushTask(ctx, CompilingStage.TYPES_DEFINE) {
                if (node.tkOperation.text == "obj") {
                    cn.visitField(Opcodes.ACC_PUBLIC + Opcodes.ACC_STATIC + Opcodes.ACC_FINAL, "INSTANCE", cn.name.desc, null, null)
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
                if (node.tkOperation.text == "obj") {
                    cn.methods.find { it.name == "<init>" && it.desc == "()V" } ?:
                    cn.visitMethod(Opcodes.ACC_PRIVATE, "<init>", "()V", null, null).run {
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
}