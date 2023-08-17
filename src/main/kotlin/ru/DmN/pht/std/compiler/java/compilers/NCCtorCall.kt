package ru.DmN.pht.std.compiler.java.compilers

import org.objectweb.asm.Opcodes
import ru.DmN.pht.base.compiler.java.Compiler
import ru.DmN.pht.base.compiler.java.compilers.NodeCompiler
import ru.DmN.pht.base.compiler.java.ctx.CompilationContext
import ru.DmN.pht.base.parser.ast.NodeNodesList
import ru.DmN.pht.base.utils.Variable
import ru.DmN.pht.std.ast.NodeConstructorCall

object NCCtorCall : NodeCompiler<NodeNodesList>() {
    override fun compile(node: NodeNodesList, compiler: Compiler, ctx: CompilationContext, ret: Boolean): Variable? {
        if (ctx.type.clazz && ctx.type.method && ctx.type.body) {
            var type = ctx.clazz!!.clazz
            if (compiler.computeStringConst(node.nodes.first(), ctx) == "super")
                type = type.superclass!!
            NCMethodCall.compileWithOutRet(
                compiler,
                ctx,
                type,
                "<init>",
                node.nodes.drop(1),
                { ctx.method!!.node.visitVarInsn(Opcodes.ALOAD, ctx.body!!["this"]!!.id) },
                enumCtor = false,
                special = true
            )
        }
        return null
    }
}