package ru.DmN.pht.std.compiler.java.compilers

import org.objectweb.asm.Opcodes
import ru.DmN.pht.base.compiler.java.Compiler
import ru.DmN.pht.base.compiler.java.ctx.CompilationContext
import ru.DmN.pht.base.utils.Variable
import ru.DmN.pht.base.utils.VirtualType
import ru.DmN.pht.std.ast.NodeThisCall
import ru.DmN.pht.std.compiler.java.utils.body
import ru.DmN.pht.std.compiler.java.utils.clazz
import ru.DmN.pht.std.compiler.java.utils.method

object NCThisCall : IStdNodeCompiler<NodeThisCall> {
    override fun calc(node: NodeThisCall, compiler: Compiler, ctx: CompilationContext): VirtualType? =
        NCMethodCall.calcType(
            compiler,
            ctx,
            ctx.clazz.clazz.methods.filter { it.name == node.name },
            node.nodes
        )

    override fun compile(node: NodeThisCall, compiler: Compiler, ctx: CompilationContext, ret: Boolean): Variable? =
        NCMethodCall.compileWithRet(
            node,
            compiler,
            ctx,
            ret,
            ctx.clazz.clazz,
            node.name,
            node.nodes,
            { ctx.method.node.visitVarInsn(Opcodes.ALOAD, ctx.body["this"]!!.id) },
            special = false
        )
}