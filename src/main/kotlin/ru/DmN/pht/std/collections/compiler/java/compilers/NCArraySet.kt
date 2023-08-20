package ru.DmN.pht.std.collections.compiler.java.compilers

import org.objectweb.asm.Opcodes
import ru.DmN.pht.base.compiler.java.Compiler
import ru.DmN.pht.base.compiler.java.ctx.CompilationContext
import ru.DmN.pht.base.parser.ast.NodeNodesList
import ru.DmN.pht.base.utils.Variable
import ru.DmN.pht.base.utils.VirtualType
import ru.DmN.pht.std.compiler.java.compilers.IStdNodeCompiler
import ru.DmN.pht.std.compiler.java.utils.global
import ru.DmN.pht.std.compiler.java.utils.method
import ru.DmN.pht.std.utils.load

object NCArraySet : IStdNodeCompiler<NodeNodesList> {
    override fun calc(node: NodeNodesList, compiler: Compiler, ctx: CompilationContext): VirtualType? =
        compiler.calc(node.nodes.first(), ctx)!!.componentType

    override fun compile(node: NodeNodesList, compiler: Compiler, ctx: CompilationContext, ret: Boolean): Variable? =
        ctx.method.node.run {
            val type = compiler.compile(node.nodes[0], ctx, true)!!.apply { load(this, this@run) }.type!!
            load(compiler.compile(node.nodes[1], ctx, true)!!, this)
            load(compiler.compile(node.nodes[2], ctx, true)!!, this)
            if (ret)
                visitInsn(Opcodes.DUP_X2)
            visitInsn(
                when (type) {
                    "[Z", "[B" -> Opcodes.BASTORE
                    "[S" -> Opcodes.SASTORE
                    "[C" -> Opcodes.CASTORE
                    "[I" -> Opcodes.IASTORE
                    "[L" -> Opcodes.IASTORE
                    "[F" -> Opcodes.IASTORE
                    "[D" -> Opcodes.IASTORE
                    else -> Opcodes.AASTORE
                }
            )
            Variable("pht$${node.hashCode()}", ctx.global.getType(compiler, type).componentType!!.className, -1, true)
        }
}