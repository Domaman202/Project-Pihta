package ru.DmN.pht.std.base.compiler.java.compilers

import org.objectweb.asm.Label
import org.objectweb.asm.Opcodes
import org.objectweb.asm.tree.ClassNode
import org.objectweb.asm.tree.MethodNode
import ru.DmN.pht.base.compiler.java.Compiler
import ru.DmN.pht.base.compiler.java.ctx.CompilationContext
import ru.DmN.pht.base.compiler.java.utils.CompileStage
import ru.DmN.pht.base.parser.ast.Node
import ru.DmN.pht.base.parser.ast.NodeNodesList
import ru.DmN.pht.base.utils.*
import ru.DmN.pht.std.base.compiler.java.ctx.BodyContext
import ru.DmN.pht.std.base.compiler.java.ctx.ClassContext
import ru.DmN.pht.std.base.compiler.java.ctx.MethodContext
import ru.DmN.pht.std.base.compiler.java.utils.*
import ru.DmN.pht.std.base.utils.insertRet
import kotlin.math.absoluteValue

object NCLambda : IStdNodeCompiler<NodeNodesList> {
    override fun calc(node: NodeNodesList, compiler: Compiler, ctx: CompilationContext): VirtualType =
        VirtualType.ofKlass("kotlin.jvm.functions.Function" + NCFn.parseArgs(node.nodes.map { compiler.compute<Any?>(it, ctx, ComputeType.NODE) }[0] as List<Node>, compiler, ctx).size)

    override fun compile(node: NodeNodesList, compiler: Compiler, ctx: CompilationContext, ret: Boolean): Variable? {
        val gctx = ctx.global
        //
        val parts = node.nodes.map { compiler.compute<Any?>(it, ctx, ComputeType.NODE) }
        val itfNode = parts.first()
        val offset = if (itfNode is Node) 1 else 0
        val args0 = NCFn.parseArgs(parts[offset] as List<Node>, compiler, ctx)
        val itf =  if (offset == 0)
            "kotlin.jvm.functions.Function" + args0.size
        else compiler.computeName(itfNode as Node, ctx)
        val imethod = gctx.getType(compiler, itf).methods.find { !it.static }!!
        //
        val generics = Generics()
        val cnode = ClassNode()
        val type = VirtualType(
            "pht.std.gen.Lambda${node.hashCode().absoluteValue}",
            mutableListOf(VirtualType.OBJECT, VirtualType.ofKlass(itf)),
            generics = generics
        )
        val cctx = ClassContext(cnode, type)
        compiler.classes += Pair(type, cnode)
        //
        compiler.pushTask(ctx, CompileStage.TYPES_DEFINE) {
            cnode.visit(
                Opcodes.V19,
                Opcodes.ACC_PUBLIC + Opcodes.ACC_FINAL,
                type.className,
                null,
                "java/lang/Object",
                arrayOf(itf.className)
            )
            cnode.visitField(
                Opcodes.ACC_PUBLIC + Opcodes.ACC_STATIC + Opcodes.ACC_FINAL,
                "INSTANCE",
                type.desc,
                null,
                null
            )
            type.fields += VirtualField("INSTANCE", type, static = true, enum = false)
            cnode.visitMethod(Opcodes.ACC_PUBLIC + Opcodes.ACC_STATIC, "<clinit>", "()V", null, null).run {
                visitCode()
                visitTypeInsn(Opcodes.NEW, cnode.name)
                visitInsn(Opcodes.DUP)
                visitMethodInsn(Opcodes.INVOKESPECIAL, cnode.name, "<init>", "()V", false)
                visitFieldInsn(Opcodes.PUTSTATIC, cnode.name, "INSTANCE", type.desc)
                visitInsn(Opcodes.RETURN)
                visitEnd()
            }
            //
            compiler.pushTask(ctx, CompileStage.METHODS_PREDEFINE) {
                if (type.methods.find { it.name == "<init>" } == null) {
                    val method =
                        VirtualMethod(
                            type,
                            "<init>",
                            TypeOrGeneric.of(generics, VirtualType.VOID),
                            emptyList(),
                            emptyList(),
                            generics = generics
                        )
                    type.methods += method
                    val mnode =
                        cnode.visitMethod(Opcodes.ACC_PRIVATE, "<init>", "()V", null, null) as MethodNode
                    val mcontext = MethodContext(mnode, method)
                    cctx.methods += mcontext
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
                //
                val args1 = NCFn.parseArgs(args0, compiler, cctx, gctx, generics)
                val body = parts[offset + 1] as Node
                //
                var access = Opcodes.ACC_PUBLIC
                if (node.attributes.getOrPut("varargs") { false } as Boolean)
                    access += Opcodes.ACC_VARARGS
                val mnode = cnode.visitMethod(
                    access,
                    imethod.name,
                    NCFn.getDescriptor(args1.second, compiler.typeOf(imethod.rettype.type)),
                    null,
                    null
                ) as MethodNode
                val method = VirtualMethod(
                    type,
                    imethod.name,
                    imethod.rettype,
                    args1.third,
                    args0.map { it.first },
                    node.attributes.getOrPut("varargs") { false } as Boolean,
                    generics = generics
                )
                type.methods += method
                val context = MethodContext(mnode, method)
                cctx.methods += context
                compiler.pushTask(ctx, CompileStage.METHODS_DEFINE) {
                    method.override = imethod.apply {
                        if (desc != mnode.desc) {
                            cctx.node.visitMethod(
                                Opcodes.ACC_PUBLIC + Opcodes.ACC_SYNTHETIC + Opcodes.ACC_BRIDGE,
                                name,
                                desc,
                                null,
                                null
                            ).apply {
                                val labelStart = Label()
                                visitLabel(labelStart)
                                visitVarInsn(Opcodes.ALOAD, 0)
                                argsc.forEachIndexed { i, it ->
                                    if (it.type.isPrimitive()) {
                                        visitVarInsn(
                                            when (it.type) {
                                                "void" -> throw RuntimeException()
                                                "boolean", "byte", "short", "char", "int" -> Opcodes.ILOAD
                                                "long" -> Opcodes.LLOAD
                                                "float" -> Opcodes.FLOAD
                                                "double" -> Opcodes.DLOAD
                                                else -> throw Error("Unreachable code")
                                            },
                                            i + 1
                                        )
                                    } else {
                                        visitVarInsn(Opcodes.ALOAD, i + 1)
                                        visitTypeInsn(Opcodes.CHECKCAST, method.argsc[i].type.replace('.', '/'))
                                    }
                                }
                                visitMethodInsn(Opcodes.INVOKEVIRTUAL, type.className, name, method.desc, false)
                                visitInsn(
                                    when (rettype.type) {
                                        "void" -> Opcodes.RETURN
                                        "boolean", "byte", "short", "char", "int" -> Opcodes.IRETURN
                                        "long" -> Opcodes.LRETURN
                                        "float" -> Opcodes.FRETURN
                                        "double" -> Opcodes.DRETURN
                                        else -> Opcodes.ARETURN
                                    }
                                )
                                val labelStop = Label()
                                visitLabel(labelStop)
                                visitLocalVariable(
                                    "this",
                                    type.desc,
                                    cctx.node.signature,
                                    labelStart,
                                    labelStop,
                                    0
                                )
                                visitEnd()
                            }
                        }
                    }
                    compiler.pushTask(ctx, CompileStage.METHODS_BODY) {
                        mnode.visitCode()
                        val bctx = BodyContext.of(context)
                        val label = Label()
                        mnode.visitLabel(label)
                        context.variableStarts[bctx.addVariable("this", type.name).id] = label
                        args0.forEach {
                            val label = Label()
                            mnode.visitLabel(label)
                            context.variableStarts[bctx.addVariable(it.first, it.second).id] = label
                        }
                        val rettype = compiler.typeOf(imethod.rettype.type)
                        insertRet(compiler.compile(body, ctx.with(context).with(bctx), rettype.name != "void"), rettype, mnode)
                        val stopLabel = Label()
                        mnode.visitLabel(stopLabel)
                        bctx.stopLabel = stopLabel
                        bctx.visitAllVariables(compiler, gctx, cctx, context)
                        mnode.visitEnd()
                    }
                }
            }
        }
        //
        return if (ret)
            ctx.method.node.run {
                visitFieldInsn(Opcodes.GETSTATIC, type.className, "INSTANCE", type.desc)
                Variable("pht$${node.hashCode()}", itf, -1, true)
            }
        else null
    }
}