package ru.DmN.pht.std.compiler.java.compilers

import org.objectweb.asm.Label
import org.objectweb.asm.Opcodes
import org.objectweb.asm.tree.MethodNode
import ru.DmN.pht.base.compiler.java.Compiler
import ru.DmN.pht.base.compiler.java.compilers.NodeCompiler
import ru.DmN.pht.base.compiler.java.ctx.BodyContext
import ru.DmN.pht.base.compiler.java.ctx.CompilationContext
import ru.DmN.pht.base.compiler.java.ctx.MethodContext
import ru.DmN.pht.base.utils.TypeOrGeneric
import ru.DmN.pht.base.utils.Variable
import ru.DmN.pht.base.utils.VirtualMethod
import ru.DmN.pht.std.ast.NodeFunction

object NCEnumCtor : NodeCompiler<NodeFunction>() {
    override fun compile(node: NodeFunction, compiler: Compiler, ctx: CompilationContext, ret: Boolean): Variable? {
        if (ctx.type.enum) {
            val cctx = ctx.cctx!!
            cctx.node.visitMethod(
                if (node.varargs) Opcodes.ACC_PRIVATE + Opcodes.ACC_VARARGS else Opcodes.ACC_PRIVATE,
                "<init>",
                "(Ljava/lang/String;I${cctx.getDescriptor(compiler, ctx.gctx, node).let { it.substring(1, it.lastIndexOf(')')) }})V",
                null,
                null
            ).run {
                val method = VirtualMethod(
                    cctx.clazz,
                    "<init>",
                    TypeOrGeneric.of(Void::class.java),
                    node.args.list.map { TypeOrGeneric.of(ctx.gctx.getType(compiler, it.second)) },
                    node.args.list.map { it.first },
                    node.args.varargs
                )
                cctx.clazz.methods += method
                val mctx = MethodContext(this as MethodNode, method)
                cctx.methods += mctx
                val bctx = BodyContext.of(mctx)
                val context = ctx.with(CompilationContext.Type.CLASS_METHOD_BODY).with(mctx).with(bctx)
                bctx.addVariable("this", cctx.clazz.name, false)
                bctx.addVariable("pht\$internal$0", "java.lang.String", false)
                bctx.addVariable("pht\$internal$1", "int", false)
                node.args.list.forEach {
                    val label = Label()
                    visitLabel(label)
                    mctx.variableStarts[bctx.addVariable(it.first, it.second).id] = label
                }
                val start = Label()
                visitLabel(start)
                visitVarInsn(Opcodes.ALOAD, 0)
                visitVarInsn(Opcodes.ALOAD, 1)
                visitVarInsn(Opcodes.ILOAD, 2)
                visitMethodInsn(Opcodes.INVOKESPECIAL, "java/lang/Enum", "<init>", "(Ljava/lang/String;I)V", false)
                node.nodes.forEach { compiler.compile(it, context, false) }
                visitInsn(Opcodes.RETURN)
                val stopLabel = Label()
                visitLabel(stopLabel)
                bctx.stopLabel = stopLabel
                bctx.visitAllVariables(compiler, ctx.gctx, cctx, mctx)
            }
        }
        return null
    }
}