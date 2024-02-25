package ru.DmN.pht.compiler.java.compilers

import org.objectweb.asm.Opcodes
import org.objectweb.asm.tree.ClassNode
import ru.DmN.pht.ast.NodeType
import ru.DmN.pht.compiler.java.ctx.ClassContext
import ru.DmN.pht.compiler.java.utils.classes
import ru.DmN.pht.compiler.java.utils.method
import ru.DmN.pht.compiler.java.utils.with
import ru.DmN.pht.jvm.compilers.IStdNodeCompiler
import ru.DmN.pht.node.NodeTypes.*
import ru.DmN.siberia.Compiler
import ru.DmN.siberia.compiler.ctx.CompilationContext
import ru.DmN.siberia.compiler.utils.CompilingStage
import ru.DmN.siberia.compilers.NCDefault
import ru.DmN.siberia.utils.Variable
import ru.DmN.siberia.utils.desc

object NCClass : IStdNodeCompiler<NodeType, ClassNode, Nothing> {
    override fun compile(node: NodeType, compiler: Compiler, ctx: CompilationContext) {
        compileAsm(node, compiler, ctx)
    }

    override fun compileVal(node: NodeType, compiler: Compiler, ctx: CompilationContext): Variable =
        compileValAsm(node, compiler, ctx).first

    override fun compileAsm(node: NodeType, compiler: Compiler, ctx: CompilationContext): ClassNode =
        ClassNode().apply {
            compiler.stageManager.pushTask(CompilingStage.TYPES_PREDEFINE) {
                compiler.contexts.classes[node.type.name] = this
                visit(
                    jcv,
                    Opcodes.ACC_PUBLIC.let {
                        when (node.info.type) {
                            ENUM_ -> it + Opcodes.ACC_ENUM
                            ITF_ -> it + Opcodes.ACC_INTERFACE + Opcodes.ACC_ABSTRACT
                            else ->
                                if (node.abstract) it + Opcodes.ACC_ABSTRACT
                                else if (node.open) it
                                else it + Opcodes.ACC_FINAL
                        }
                    },
                    node.type.className,
                    node.type.signature,
                    node.type.superclass?.className ?: "java/lang/Object",
                    node.type.interfaces.map { it.className }.toTypedArray()
                )
            }
            compiler.stageManager.pushTask(CompilingStage.TYPES_DEFINE) {
                if (node.info.type == OBJ_) {
                    visitField(
                        Opcodes.ACC_PUBLIC + Opcodes.ACC_STATIC + Opcodes.ACC_FINAL,
                        "INSTANCE",
                        name.desc,
                        null,
                        null
                    )
                    visitMethod(Opcodes.ACC_STATIC, "<clinit>", "()V", null, null).run {
                        visitCode()
                        visitTypeInsn(Opcodes.NEW, name)
                        visitInsn(Opcodes.DUP)
                        visitMethodInsn(Opcodes.INVOKESPECIAL, name, "<init>", "()V", false)
                        visitFieldInsn(Opcodes.PUTSTATIC, name, "INSTANCE", name.desc)
                        visitInsn(Opcodes.RETURN)
                        visitEnd()
                    }
                }
                NCDefault.compile(node, compiler, ctx.with(ClassContext(this, node.type)))
                if (node.info.type == OBJ_) {
                    methods.find { it.name == "<init>" && it.desc == "()V" } ?: visitMethod(
                        Opcodes.ACC_PRIVATE,
                        "<init>",
                        "()V",
                        null,
                        null
                    ).run {
                        visitCode()
                        visitVarInsn(Opcodes.ALOAD, 0)
                        visitMethodInsn(Opcodes.INVOKESPECIAL, superName, "<init>", "()V", false)
                        visitInsn(Opcodes.RETURN)
                        visitEnd()
                    }
                }
            }
        }

    override fun compileValAsm(node: NodeType, compiler: Compiler, ctx: CompilationContext): Pair<Variable, ClassNode> =
        Pair(Variable.tmp(node, node.type), compileAsm(node, compiler, ctx)).apply {
            compiler.stageManager.pushTask(CompilingStage.METHODS_BODY) {
                ctx.method.node.visitFieldInsn(Opcodes.GETSTATIC, node.type.className, "INSTANCE", node.type.desc)
            }
        }

    private val jcv: Int
    init {
        var version = System.getProperty("java.version")
        if (version.startsWith("1.")) {
            version = version.substring(2, 3)
        } else {
            val dot = version.indexOf(".")
            if (dot != -1) {
                version = version.substring(0, dot)
            }
        }
        jcv = Opcodes.V1_2 + version.toInt() - 2
    }
}