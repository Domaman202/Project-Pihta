package ru.DmN.pht.std.compiler.java.compilers

import org.objectweb.asm.Label
import org.objectweb.asm.Opcodes
import org.objectweb.asm.tree.ClassNode
import org.objectweb.asm.tree.MethodNode
import ru.DmN.pht.base.compiler.java.CompileStage
import ru.DmN.pht.base.compiler.java.Compiler
import ru.DmN.pht.base.compiler.java.compilers.NodeCompiler
import ru.DmN.pht.base.compiler.java.ctx.ClassContext
import ru.DmN.pht.base.compiler.java.ctx.CompilationContext
import ru.DmN.pht.base.compiler.java.ctx.MethodContext
import ru.DmN.pht.base.utils.*
import ru.DmN.pht.std.ast.NodeClass

object NCClass : NodeCompiler<NodeClass>() {
    override fun compile(node: NodeClass, compiler: Compiler, ctx: CompilationContext, ret: Boolean): Variable? {
        if (ctx.type == CompilationContext.Type.GLOBAL) { // todo: subclass
            compiler.tasks[CompileStage.TYPES_PREDEFINE].add {
                val isInterface = node.tkOperation.text == "intf"
                val isClass = node.tkOperation.text == "cls"
                val isObject = node.tkOperation.text == "obj"
                //
                val cnode = ClassNode()
                val type = VirtualType(ctx.global.name(node.name), isInterface = isInterface, generics = node.generics)
                val context = ClassContext(cnode, type)
                compiler.classes += context
                compiler.tasks[CompileStage.TYPES_DEFINE].add {
                    type.parents = node.parents.map { context.getType(compiler, ctx.global, it) }.toMutableList()
                        .apply { if (!isInterface && isEmpty()) this += VirtualType.ofKlass(Any::class.java) }
                    cnode.visit(
                        Opcodes.V19,
                        if (isInterface)
                            Opcodes.ACC_PUBLIC + Opcodes.ACC_ABSTRACT + Opcodes.ACC_INTERFACE
                        else if (isClass)
                            Opcodes.ACC_PUBLIC
                        else if (isObject)
                            Opcodes.ACC_PUBLIC + Opcodes.ACC_FINAL
                        else throw Error(),
                        type.className,
                        node.getSignature(compiler, context, ctx.global),
                        type.parents.firstOrNull()?.className ?: "java/lang/Object",
                        type.parents.drop(1).map { it.className }.toTypedArray()
                    )
                    if (isObject) {
                        type.fields += VirtualField("INSTANCE", type, static = true, enum = false)
                        cnode.visitField(
                            Opcodes.ACC_PUBLIC + Opcodes.ACC_STATIC + Opcodes.ACC_FINAL,
                            "INSTANCE",
                            type.desc,
                            null,
                            null
                        )
                        cnode.visitMethod(Opcodes.ACC_PUBLIC + Opcodes.ACC_STATIC, "<clinit>", "()V", null, null).run {
                            visitCode()
                            visitTypeInsn(Opcodes.NEW, cnode.name)
                            visitInsn(Opcodes.DUP)
                            visitMethodInsn(Opcodes.INVOKESPECIAL, cnode.name, "<init>", "()V", false)
                            visitFieldInsn(Opcodes.PUTSTATIC, cnode.name, "INSTANCE", type.desc)
                            visitInsn(Opcodes.RETURN)
                            visitEnd()
                        }
                    }
                    compiler.tasks[CompileStage.METHODS_PREDEFINE].add {
                        val nctx = ctx.with(CompilationContext.Type.CLASS).with(context)
                        node.nodes.forEach { compiler.compile(it, nctx, false) }
                        if (isObject) {
                            if (type.methods.find { it.name == "<init>" } == null) {
                                val method = VirtualMethod(
                                    type,
                                    "<init>",
                                    TypeOrGeneric.of(Void::class.javaPrimitiveType!!),
                                    emptyList(), emptyList(), varargs = false,
                                    static = false,
                                    abstract = isInterface
                                )
                                type.methods += method
                                val mnode =
                                    cnode.visitMethod(Opcodes.ACC_PRIVATE, "<init>", "()V", null, null) as MethodNode
                                val mcontext = MethodContext(mnode, method)
                                context.methods += mcontext
                                mnode.run {
                                    visitCode()
                                    val labelStart = Label()
                                    visitLabel(labelStart)
                                    visitVarInsn(Opcodes.ALOAD, 0)
                                    visitMethodInsn(Opcodes.INVOKESPECIAL, cnode.superName, "<init>", "()V", false)
                                    visitInsn(Opcodes.RETURN)
                                    val labelStop = Label()
                                    visitLabel(labelStop)
                                    visitLocalVariable("this", type.desc, null, labelStart, labelStop, 0)
                                    visitEnd()
                                }
                            }
                        }
                    }
                }
            }
        }
        return null
    }
}