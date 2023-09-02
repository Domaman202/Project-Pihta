package ru.DmN.pht.std.base.compiler.java.compilers

import org.objectweb.asm.Label
import org.objectweb.asm.Opcodes
import org.objectweb.asm.tree.MethodNode
import ru.DmN.pht.base.compiler.java.Compiler
import ru.DmN.pht.base.compiler.java.ctx.CompilationContext
import ru.DmN.pht.base.compiler.java.utils.CompileStage
import ru.DmN.pht.base.parser.ast.Node
import ru.DmN.pht.base.parser.ast.NodeNodesList
import ru.DmN.pht.base.utils.*
import ru.DmN.pht.std.base.compiler.java.ctx.ClassContext
import ru.DmN.pht.std.base.compiler.java.ctx.GlobalContext
import ru.DmN.pht.std.base.compiler.java.ctx.MethodContext
import ru.DmN.pht.std.base.compiler.java.utils.*

object NCBridgeFn : IStdNodeCompiler<NodeNodesList> {
    override fun compile(node: NodeNodesList, compiler: Compiler, ctx: CompilationContext, ret: Boolean): Variable? {
        val gctx = ctx.global
        val cctx = ctx.clazz
        val clazz = cctx.clazz
        val generics = clazz.generics
        //
        val parts = node.nodes.map { { type: ComputeType -> compiler.compute<Any?>(it, ctx, type) } }
        val name = parts[0](ComputeType.NAME) as String
        val returnClass = parts[1](ComputeType.NAME) as String
        val returnType = cctx.getType(compiler, gctx, returnClass)
        //
        val src = parseArgs(parts[2], compiler, ctx, cctx, gctx, generics)
        val to = parseArgs(parts[3], compiler, ctx, cctx, gctx, generics)
        //
        if (src.second != to.second) {
            //
            val final = node.attributes.getOrPut("final") { false } as Boolean
            val static = node.attributes.getOrPut("static") { false } as Boolean
            val varargs = node.attributes.getOrPut("varargs") { false } as Boolean
            //
            var access = Opcodes.ACC_PUBLIC + Opcodes.ACC_SYNTHETIC + Opcodes.ACC_BRIDGE
            if (final)
                access += Opcodes.ACC_FINAL
            else if (static)
                access += Opcodes.ACC_STATIC
            if (varargs)
                access += Opcodes.ACC_VARARGS
            //
            val mnode = cctx.node.visitMethod(
                access,
                name,
                NCDefn.getDescriptor(src.second, returnType),
                NCDefn.getSignature(src.first, returnClass, varargs) { ctx.clazz.getType(compiler, gctx, it) },
                null
            ) as MethodNode
            val method = VirtualMethod(
                clazz,
                name,
                TypeOrGeneric.of(generics, returnType),
                src.third,
                List(src.first.size) { i -> "arg$i" },
                varargs,
                static,
                false,
                null,
                null,
                generics
            )
            clazz.methods += method
            val context = MethodContext(mnode, method)
            cctx.methods += context
            compiler.pushTask(ctx, CompileStage.METHODS_DEFINE) {
                mnode.apply {
                    val labelStart = Label()
                    visitLabel(labelStart)
                    visitVarInsn(Opcodes.ALOAD, 0)
                    method.argsc.forEachIndexed { i, it ->
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
                            visitTypeInsn(Opcodes.CHECKCAST, to.second[i].className)
                        }
                    }
                    visitMethodInsn(
                        Opcodes.INVOKEVIRTUAL,
                        clazz.className,
                        name,
                        NCDefn.getDescriptor(to.second, returnType),
                        false
                    )
                    visitInsn(
                        when (method.rettype.type) {
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
                    visitLocalVariable("this", clazz.desc, cctx.node.signature, labelStart, labelStop, 0)
                    visitEnd()
                }
            }
        }
        //
        return null
    }

    private fun parseArgs(
        node: (type: ComputeType) -> Any?,
        compiler: Compiler,
        ctx: CompilationContext,
        cctx: ClassContext,
        gctx: GlobalContext,
        generics: Generics
    ): Triple<List<String>, List<VirtualType>, List<TypeOrGeneric>> {
        val srcArgs = (node(ComputeType.NODE) as List<Node>).map { compiler.computeName(it, ctx) }
        val srcArgsTypes = srcArgs.map { cctx.getType(compiler, gctx, it) }
        return Triple(srcArgs, srcArgsTypes, srcArgsTypes.mapIndexed { i, it -> TypeOrGeneric.of(generics, srcArgs[i], it) })
    }
}