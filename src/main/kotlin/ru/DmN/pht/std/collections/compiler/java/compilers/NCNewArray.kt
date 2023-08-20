package ru.DmN.pht.std.collections.compiler.java.compilers

import org.objectweb.asm.Opcodes
import ru.DmN.pht.base.compiler.java.Compiler
import ru.DmN.pht.base.compiler.java.ctx.CompilationContext
import ru.DmN.pht.base.parser.ast.NodeNodesList
import ru.DmN.pht.base.utils.Variable
import ru.DmN.pht.base.utils.VirtualType
import ru.DmN.pht.std.compiler.java.compilers.IStdNodeCompiler
import ru.DmN.pht.std.compiler.java.utils.*

object NCNewArray : IStdNodeCompiler<NodeNodesList> {
    override fun calc(node: NodeNodesList, compiler: Compiler, ctx: CompilationContext): VirtualType =
        type(node, compiler, ctx)

    override fun compile(node: NodeNodesList, compiler: Compiler, ctx: CompilationContext, ret: Boolean): Variable =
        ctx.method.node.run {
            val type = type(node, compiler, ctx)
            visitLdcInsn(compiler.computeInt(node.nodes.last(), ctx))
            visitTypeInsn(Opcodes.ANEWARRAY, type.componentType!!.className)
            Variable("pht$${node.hashCode()}", type.name, -1, true)
        }

    fun type(node: NodeNodesList, compiler: Compiler, ctx: CompilationContext) =
        ctx.global.getType(compiler, compiler.computeName(node.nodes.first(), ctx)).arrayType
}