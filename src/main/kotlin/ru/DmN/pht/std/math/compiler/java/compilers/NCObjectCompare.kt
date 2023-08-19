package ru.DmN.pht.std.math.compiler.java.compilers

import org.objectweb.asm.Opcodes
import ru.DmN.pht.base.compiler.java.Compiler
import ru.DmN.pht.base.compiler.java.ctx.CompilationContext
import ru.DmN.pht.base.parser.ast.NodeNodesList
import ru.DmN.pht.base.utils.Variable
import ru.DmN.pht.base.utils.VirtualType
import ru.DmN.pht.std.compiler.java.compilers.IStdNodeCompiler
import ru.DmN.pht.std.compiler.java.method
import ru.DmN.pht.std.utils.loadCast

object NCObjectCompare : IStdNodeCompiler<NodeNodesList> {
    override fun calc(node: NodeNodesList, compiler: Compiler, ctx: CompilationContext): VirtualType? =
        VirtualType.INT

    override fun compile(node: NodeNodesList, compiler: Compiler, ctx: CompilationContext, ret: Boolean): Variable? =
        if (ret)
            ctx.method.node.run {
                loadCast(compiler.compile(node.nodes.first(), ctx, true)!!, VirtualType.ofKlass(Comparable::class.java), this)
                loadCast(compiler.compile(node.nodes.last(), ctx, true)!!, VirtualType.OBJECT, this)
                visitMethodInsn(Opcodes.INVOKEINTERFACE, "java/lang/Comparable", "compareTo", "(Ljava/lang/Object;)I", true)
                Variable("lul$${node.hashCode()}", "int", -1, true)
            }
        else null
}