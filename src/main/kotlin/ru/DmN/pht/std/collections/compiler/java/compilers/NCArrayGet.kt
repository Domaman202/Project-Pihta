package ru.DmN.pht.std.collections.compiler.java.compilers

import org.objectweb.asm.Opcodes
import ru.DmN.pht.base.Compiler
import ru.DmN.pht.base.compiler.java.ctx.CompilationContext
import ru.DmN.pht.base.parser.ast.NodeNodesList
import ru.DmN.pht.base.utils.Variable
import ru.DmN.pht.base.utils.VirtualType
import ru.DmN.pht.std.base.compiler.java.compilers.IStdNodeCompiler
import ru.DmN.pht.std.base.compiler.java.utils.global
import ru.DmN.pht.std.base.compiler.java.utils.method
import ru.DmN.pht.std.base.utils.load

object NCArrayGet : IStdNodeCompiler<NodeNodesList> {
    override fun calc(node: NodeNodesList, compiler: Compiler, ctx: CompilationContext): VirtualType? =
        compiler.calc(node.nodes.first(), ctx)!!.componentType

    override fun compile(node: NodeNodesList, compiler: Compiler, ctx: CompilationContext, ret: Boolean): Variable? =
        if (ret)
            ctx.method.node.run {
                val type = compiler.compile(node.nodes.first(), ctx, true)!!.apply { load(this, this@run) }.type!!
                load(compiler.compile(node.nodes.last(), ctx, true)!!, this)
                visitInsn(when (type) {
                    "[Z", "[B" -> Opcodes.BALOAD
                    "[S" -> Opcodes.SALOAD
                    "[C" -> Opcodes.CALOAD
                    "[I" -> Opcodes.IALOAD
                    "[L" -> Opcodes.IALOAD
                    "[F" -> Opcodes.IALOAD
                    "[D" -> Opcodes.IALOAD
                    else -> Opcodes.AALOAD
                })
                Variable("pht$${node.hashCode()}", ctx.global.getType(compiler, type).componentType!!.className, -1, true)
            }
        else null
}