package ru.DmN.pht.std.compiler.java.compilers

import org.objectweb.asm.Opcodes
import ru.DmN.pht.base.compiler.java.Compiler
import ru.DmN.pht.base.compiler.java.compilers.NodeCompiler
import ru.DmN.pht.base.compiler.java.ctx.CompilationContext
import ru.DmN.pht.base.utils.Variable
import ru.DmN.pht.base.utils.VirtualType
import ru.DmN.pht.std.ast.NodeThisCall

object NCThisCall : NodeCompiler<NodeThisCall>() {
    override fun calcType(node: NodeThisCall, compiler: Compiler, ctx: CompilationContext): VirtualType? =
        if (ctx.type.clazz && ctx.type.method && ctx.type.body)
            NCMethodCall.calcType(
                compiler,
                ctx,
                ctx.clazz!!.clazz.methods.filter { it.name == node.name },
                node.nodes
            )
        else null

    override fun compile(node: NodeThisCall, compiler: Compiler, ctx: CompilationContext, ret: Boolean): Variable? =
        if (ctx.type.clazz && ctx.type.method && ctx.type.body) {
            NCMethodCall.compileWithRet(
                node,
                compiler,
                ctx,
                ret,
                ctx.clazz!!.clazz,
                node.name,
                node.nodes,
                {ctx.method!!.node.visitVarInsn(Opcodes.ALOAD, ctx.body!!["this"]!!.id)},
                special = false
            )
        } else null
}