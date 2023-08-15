package ru.DmN.pht.std.compiler.java.compilers

import org.objectweb.asm.Label
import org.objectweb.asm.Opcodes
import org.objectweb.asm.tree.MethodNode
import ru.DmN.pht.base.compiler.java.CompileStage
import ru.DmN.pht.base.compiler.java.Compiler
import ru.DmN.pht.base.compiler.java.compilers.NodeCompiler
import ru.DmN.pht.base.compiler.java.ctx.BodyContext
import ru.DmN.pht.base.compiler.java.ctx.CompilationContext
import ru.DmN.pht.base.compiler.java.ctx.MethodContext
import ru.DmN.pht.base.utils.TypeOrGeneric
import ru.DmN.pht.base.utils.Variable
import ru.DmN.pht.base.utils.VirtualMethod
import ru.DmN.pht.std.ast.NodeExFunction
import ru.DmN.pht.std.utils.desc
import ru.DmN.pht.std.utils.insertRet
import ru.DmN.pht.base.compiler.java.ctx.CompilationContext.Type as CtxType

object NCExFunction : NodeCompiler<NodeExFunction>() {
    override fun compile(node: NodeExFunction, compiler: Compiler, ctx: CompilationContext, ret: Boolean): Variable? {
        if (ctx.type.clazz) {
            val gctx = ctx.global
            val cctx = ctx.clazz!!
            val mnode = cctx.node.visitMethod(
                Opcodes.ACC_STATIC + Opcodes.ACC_PUBLIC + if (node.varargs) Opcodes.ACC_VARARGS else 0,
                node.name,
                "(${node.clazz.desc}${node.args.desc})${node.rettype.desc}",
                null,
                null
            ) as MethodNode
            val args = node.args.build { gctx.getType(compiler, it) }
            val method = VirtualMethod(
                cctx.clazz,
                node.name,
                TypeOrGeneric.of(gctx.getType(compiler, node.rettype)),
                args.first,
                args.second,
                node.args.varargs,
                extend = gctx.getType(compiler, node.clazz)
            )
            cctx.clazz.methods += method
            val context = MethodContext(mnode, method)
            cctx.methods += context
            compiler.tasks[CompileStage.METHODS_DEFINE].add {
                val bctx = BodyContext.of(context)
                val label0 = Label()
                mnode.visitLabel(label0)
                context.variableStarts[bctx.addVariable("this", node.clazz).id] = label0
                node.args.list.forEach {
                    val label1 = Label()
                    mnode.visitLabel(label1)
                    context.variableStarts[bctx.addVariable(it.first, it.second).id] = label1
                }
                val nctx = ctx.with(CtxType.CLASS.with(CtxType.METHOD).with(CtxType.BODY)).with(context).with(bctx)
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
        return null
    }
}