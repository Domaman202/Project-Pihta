package ru.DmN.pht.std.compiler.java.compilers

import org.objectweb.asm.Opcodes
import ru.DmN.pht.base.compiler.java.Compiler
import ru.DmN.pht.base.compiler.java.ctx.CompilationContext
import ru.DmN.pht.base.parser.ast.NodeNodesList
import ru.DmN.pht.base.utils.Variable
import ru.DmN.pht.base.utils.VirtualType
import ru.DmN.pht.std.compiler.java.utils.computeName
import ru.DmN.pht.std.compiler.java.utils.global
import ru.DmN.pht.std.compiler.java.utils.method

object NCNew : IStdNodeCompiler<NodeNodesList> {
    override fun calc(node: NodeNodesList, compiler: Compiler, ctx: CompilationContext): VirtualType? =
        ctx.global.getType(compiler, compiler.computeName(node.nodes.first(), ctx))

    override fun compile(node: NodeNodesList, compiler: Compiler, ctx: CompilationContext, ret: Boolean): Variable? =
        ctx.method.node.run {
            val type = ctx.global.getType(compiler, compiler.computeName(node.nodes.first(), ctx))
            visitTypeInsn(Opcodes.NEW, type.className)
            visitInsn(Opcodes.DUP)
            NCMethodCallA.compileWithOutRet(
                compiler,
                ctx,
                type,
                "<init>",
                node.nodes.drop(1),
                {},
                enumCtor = false,
                special = true
            )
            if (ret)
                Variable("tmp$${node.hashCode()}", type.name, -1, true)
            else {
                visitInsn(Opcodes.POP)
                null
            }
        }
}