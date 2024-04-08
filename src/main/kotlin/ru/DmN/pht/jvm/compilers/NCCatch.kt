package ru.DmN.pht.compiler.java.compilers

import org.objectweb.asm.Label
import org.objectweb.asm.Opcodes
import org.objectweb.asm.tree.MethodNode
import ru.DmN.pht.ast.NodeCatch
import ru.DmN.pht.compiler.java.ctx.BodyContext
import ru.DmN.pht.compiler.java.utils.load
import ru.DmN.pht.jvm.compiler.ctx.body
import ru.DmN.pht.jvm.compiler.ctx.method
import ru.DmN.pht.jvm.compiler.ctx.with
import ru.DmN.pht.jvm.utils.vtype.jvmName
import ru.DmN.siberia.ast.Node
import ru.DmN.siberia.compiler.Compiler
import ru.DmN.siberia.compiler.ctx.CompilationContext
import ru.DmN.siberia.compilers.INodeCompiler
import ru.DmN.siberia.compilers.NCDefault
import ru.DmN.siberia.utils.Variable
import ru.DmN.siberia.utils.vtype.VirtualType

object NCCatch : INodeCompiler<NodeCatch> {
    override fun compile(node: NodeCatch, compiler: Compiler, ctx: CompilationContext) =
        compile(node, ctx, { NCDefault.compile(node, compiler, ctx) }, { a, b, _ -> compiler.compile(a, b) })

    override fun compileVal(node: NodeCatch, compiler: Compiler, ctx: CompilationContext): Variable {
        compile(
            node,
            ctx,
            { load(NCDefault.compileVal(node, compiler, ctx), it) },
            { it, context, mctx -> load(compiler.compileVal(it, context), mctx) }
        )
        return Variable.tmp(node, node.type ?: VirtualType.VOID)
    }


    private fun compile(
        node: NodeCatch,
        ctx: CompilationContext,
        compileBody: (mctx: MethodNode) -> Unit,
        compileCatch: (node: Node, ctx: CompilationContext, mctx: MethodNode) -> Unit
    ) {
        ctx.method.node.run {
            val tryStart = Label()
            val tryStop = Label()
            val allCatchStop = Label()
            visitLabel(tryStart)
            compileBody(this)
            visitLabel(tryStop)
            visitJumpInsn(Opcodes.GOTO, allCatchStop)
            val bctx = ctx.body
            node.catchers.forEach {
                val handle = Label()
                visitLabel(handle)
                if (it.third != null) {
                    val body = BodyContext.of(bctx, handle)
                    body.add(it.first, it.second)
                    if (it.first == "_")
                        visitInsn(Opcodes.POP)
                    else visitVarInsn(Opcodes.ASTORE, body[it.first]!!.id)
                    compileCatch(it.third!!, ctx.with(body), this)
                    val handleStop = Label()
                    visitLabel(handleStop)
                    body.stop = handleStop
                }
                visitJumpInsn(Opcodes.GOTO, allCatchStop)
                visitTryCatchBlock(tryStart, tryStop, handle, it.second.jvmName)
            }
            visitLabel(allCatchStop)
        }
    }
}