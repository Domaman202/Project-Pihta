package ru.DmN.pht.std.base.compiler.java.compilers

import org.objectweb.asm.Opcodes
import ru.DmN.pht.base.compiler.java.Compiler
import ru.DmN.pht.base.compiler.java.ctx.CompilationContext
import ru.DmN.pht.base.parser.ast.Node
import ru.DmN.pht.base.parser.ast.NodeNodesList
import ru.DmN.pht.base.utils.Variable
import ru.DmN.pht.base.utils.VirtualType
import ru.DmN.pht.std.base.compiler.java.utils.computeName
import ru.DmN.pht.std.base.compiler.java.utils.global
import ru.DmN.pht.std.base.compiler.java.utils.method

object NCNew : IStdNodeCompiler<NodeNodesList> {
    override fun calc(node: NodeNodesList, compiler: Compiler, ctx: CompilationContext): VirtualType =
        ctx.global.getType(compiler, compiler.computeName(node.nodes.first(), ctx))

    override fun compile(node: NodeNodesList, compiler: Compiler, ctx: CompilationContext, ret: Boolean): Variable? =
        insertNew(ctx.global.getType(compiler, compiler.computeName(node.nodes.first(), ctx)), node.nodes.drop(1), node.hashCode(), compiler, ctx, ret)

    fun insertNew(type: VirtualType, args: List<Node>, hash: Int, compiler: Compiler, ctx: CompilationContext, ret: Boolean) =
        ctx.method.node.run {
            visitTypeInsn(Opcodes.NEW, type.className)
            visitInsn(Opcodes.DUP)
            NCMethodCallA.compileWithOutRet(
                compiler,
                ctx,
                type,
                "<init>",
                args,
                {},
                enumCtor = false,
                special = true
            )
            if (ret)
                Variable("tmp$$hash", type.name, -1, true)
            else {
                visitInsn(Opcodes.POP)
                null
            }
        }
}