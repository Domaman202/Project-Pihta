package ru.DmN.pht.std.compiler.java.compilers

import org.objectweb.asm.Opcodes
import ru.DmN.pht.base.Compiler
import ru.DmN.pht.base.compiler.java.compilers.NodeCompiler
import ru.DmN.pht.base.compiler.java.ctx.CompilationContext
import ru.DmN.pht.base.utils.Variable
import ru.DmN.pht.std.ast.NodeConstructorCall

object NCCtorCall : NodeCompiler<NodeConstructorCall>() {
    override fun compile(node: NodeConstructorCall, compiler: Compiler, ctx: CompilationContext, ret: Boolean): Variable? {
        if (ctx.type.clazz && ctx.type.method && ctx.type.body) {
            var type = ctx.cctx!!.clazz
            if (node.supercall)
                type = type.superclass!!
            NCMethodCall.compileWithOutRet(
                compiler,
                ctx,
                type,
                "<init>",
                node.nodes,
                { ctx.mctx!!.node.visitVarInsn(Opcodes.ALOAD, ctx.bctx!!["this"]!!.id) },
                enumCtor = false,
                special = true
            )
        }
        return null
    }
}