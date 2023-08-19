package ru.DmN.pht.std.compiler.java.compilers

import org.objectweb.asm.Opcodes
import ru.DmN.pht.base.compiler.java.Compiler
import ru.DmN.pht.base.compiler.java.ctx.CompilationContext
import ru.DmN.pht.base.parser.ast.NodeNodesList
import ru.DmN.pht.base.utils.Variable
import ru.DmN.pht.base.utils.VirtualType
import ru.DmN.pht.std.compiler.java.computeName
import ru.DmN.pht.std.compiler.java.global
import ru.DmN.pht.std.compiler.java.method
import ru.DmN.pht.std.utils.load

object NCIs : IStdNodeCompiler<NodeNodesList> {
    override fun calc(node: NodeNodesList, compiler: Compiler, ctx: CompilationContext): VirtualType? =
        VirtualType.BOOLEAN

    override fun compile(node: NodeNodesList, compiler: Compiler, ctx: CompilationContext, ret: Boolean): Variable? =
        if (ret)
            ctx.method.node.run {
                load(compiler.compile(node.nodes.last(), ctx, true)!!, this)
                visitTypeInsn(Opcodes.INSTANCEOF, ctx.global.getType(compiler, compiler.computeName(node.nodes.first(), ctx)).className)
                Variable("pth$${node.hashCode()}", "boolean", -1, true)
            }
        else null
}