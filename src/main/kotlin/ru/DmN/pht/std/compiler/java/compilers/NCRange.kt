package ru.DmN.pht.std.compiler.java.compilers

import org.objectweb.asm.Opcodes
import ru.DmN.pht.base.compiler.java.Compiler
import ru.DmN.pht.base.compiler.java.compilers.NodeCompiler
import ru.DmN.pht.base.compiler.java.ctx.CompilationContext
import ru.DmN.pht.base.utils.Variable
import ru.DmN.pht.base.utils.VirtualType
import ru.DmN.pht.base.parser.ast.NodeNodesList
import ru.DmN.pht.std.utils.load

object NCRange : NodeCompiler<NodeNodesList>() {
    override fun calc(node: NodeNodesList, compiler: Compiler, ctx: CompilationContext): VirtualType? =
        if (ctx.type.method)
            compiler.typeOf("java.util.Iterator")
        else null

    override fun compile(
        node: NodeNodesList,
        compiler: Compiler,
        ctx: CompilationContext,
        ret: Boolean
    ): Variable? =
        if (ctx.type.method)
            ctx.method!!.node.run {
                node.nodes.forEach { load(compiler.compile(it, ctx, true)!!, this) }
                visitMethodInsn(
                    Opcodes.INVOKESTATIC,
                    "ru/DmN/pht/std/StdBytecodeAdapter",
                    "range",
                    "(II)Ljava/util/Iterator;",
                    false
                )
                Variable("pht$${node.hashCode()}", "java.util.Iterator", -1, true)
            }
        else null
}