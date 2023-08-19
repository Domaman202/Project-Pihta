package ru.DmN.pht.std.compiler.java.compilers

import org.objectweb.asm.Opcodes
import ru.DmN.pht.base.compiler.java.Compiler
import ru.DmN.pht.base.compiler.java.ctx.CompilationContext
import ru.DmN.pht.base.parser.ast.NodeNodesList
import ru.DmN.pht.base.utils.Variable
import ru.DmN.pht.std.compiler.java.utils.body
import ru.DmN.pht.std.compiler.java.utils.clazz
import ru.DmN.pht.std.compiler.java.utils.computeName
import ru.DmN.pht.std.compiler.java.utils.method

object NCCtorCall : IStdNodeCompiler<NodeNodesList> {
    override fun compile(node: NodeNodesList, compiler: Compiler, ctx: CompilationContext, ret: Boolean): Variable? {
        var type = ctx.clazz.clazz
        if (compiler.computeName(node.nodes.first(), ctx) == "super")
            type = type.superclass!!
        NCMethodCall.compileWithOutRet(
            compiler,
            ctx,
            type,
            "<init>",
            node.nodes.drop(1),
            { ctx.method.node.visitVarInsn(Opcodes.ALOAD, ctx.body["this"]!!.id) },
            enumCtor = false,
            special = true
        )
        return null
    }
}