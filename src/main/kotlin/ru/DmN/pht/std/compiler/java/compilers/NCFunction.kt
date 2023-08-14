package ru.DmN.pht.std.compiler.java.compilers

import org.objectweb.asm.Label
import org.objectweb.asm.Opcodes
import org.objectweb.asm.tree.MethodNode
import ru.DmN.pht.base.Compiler
import ru.DmN.pht.base.compiler.java.compilers.NodeCompiler
import ru.DmN.pht.base.compiler.java.ctx.BodyContext
import ru.DmN.pht.base.compiler.java.ctx.CompilationContext
import ru.DmN.pht.base.compiler.java.ctx.MethodContext
import ru.DmN.pht.base.lexer.Token
import ru.DmN.pht.base.parser.ast.Node
import ru.DmN.pht.base.utils.TypeOrGeneric
import ru.DmN.pht.base.utils.Variable
import ru.DmN.pht.base.utils.VirtualMethod
import ru.DmN.pht.base.utils.isPrimitive
import ru.DmN.pht.std.ast.NodeFunction
import ru.DmN.pht.std.ast.NodeMethodCall
import ru.DmN.pht.std.utils.insertRet
import ru.DmN.pht.std.utils.load
import ru.DmN.pht.std.utils.loadCast

object NCFunction : NodeCompiler<NodeFunction>() {
    override fun compile(node: NodeFunction, compiler: Compiler, ctx: CompilationContext, ret: Boolean): Variable? {
        if (ctx.type.clazz) { // todo: lambda & no class method
            val gctx = ctx.gctx
            val cctx = ctx.cctx!!
            val clazz = cctx.clazz
            val mnode = cctx.node.visitMethod(
                if (node.static) Opcodes.ACC_STATIC + Opcodes.ACC_PUBLIC
                else if (node.abstract) Opcodes.ACC_ABSTRACT + Opcodes.ACC_PUBLIC
                else Opcodes.ACC_PUBLIC,
                node.name,
                cctx.getDescriptor(compiler, gctx, node),
                cctx.getSignature(node),
                null
            ) as MethodNode
            val rettype = TypeOrGeneric.of(cctx.getType(compiler, gctx, node.rettype))
            val argsc = node.args.list.map { TypeOrGeneric.of(cctx.getType(compiler, gctx, it.second)) }
            val method = VirtualMethod(
                clazz,
                node.name,
                rettype,
                argsc, node.args.list.map { it.first }, node.args.isVarArgs(),
                node.static,
                node.abstract,
                null
            )
            clazz.methods += method
            val context = MethodContext(mnode, method)
            cctx.methods += context
            if (!node.abstract) {
                compiler.getLastStack().add {
                    if (node.override) {
                        method.override = clazz.getAllMethods().find { it ->
                            it.declaringClass != clazz && it.overridableBy(method) {
                                gctx.getType(
                                    compiler,
                                    it
                                )
                            }
                        }!!.apply {
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
                                visitMethodInsn(Opcodes.INVOKEVIRTUAL, clazz.className, name, method.desc, false)
                                visitInsn(
                                    when (rettype.type) {
                                        "void" -> Opcodes.RETURN
                                        "boolean","byte","short","char","int" -> Opcodes.IRETURN
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
                    compiler.getLastStack().add {
                        val bctx = BodyContext.of(context)
                        if (!node.static) {
                            val label = Label()
                            mnode.visitLabel(label)
                            context.variableStarts[bctx.addVariable("this", clazz.name).id] = label
                        }
                        node.args.list.forEach {
                            val label = Label()
                            mnode.visitLabel(label)
                            context.variableStarts[bctx.addVariable(it.first, it.second).id] = label
                        }
                        val nctx = ctx.with(CompilationContext.Type.CLASS_METHOD_BODY).with(context).with(bctx)
                        val nodes = node.nodes.map { { r: Boolean -> compiler.compile(it, nctx, r) } }
                        nodes.dropLast(1).forEach { it(false) }
                        val retVal = method.rettype.type != "void"
                        if (nodes.isEmpty()) {
                            mnode.visitInsn(
                                if (retVal) {
                                    mnode.visitFieldInsn(Opcodes.GETSTATIC, "kotlin/Unit", "INSTANCE", "Lkotlin/Unit;")
                                    Opcodes.ARETURN
                                } else Opcodes.RETURN
                            )
                        } else insertRet(nodes.last()(retVal), gctx.getType(compiler, method.rettype.type), mnode)
                        val stopLabel = Label()
                        mnode.visitLabel(stopLabel)
                        bctx.stopLabel = stopLabel
                        bctx.visitAllVariables(compiler, gctx, cctx, context)
                    }
                }
            }
        }
        return null
    }
}