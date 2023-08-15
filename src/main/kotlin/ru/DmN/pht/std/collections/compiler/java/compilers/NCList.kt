package ru.DmN.pht.std.collections.compiler.java.compilers

import org.objectweb.asm.Opcodes
import ru.DmN.pht.base.compiler.java.Compiler
import ru.DmN.pht.base.compiler.java.compilers.NodeCompiler
import ru.DmN.pht.base.compiler.java.ctx.CompilationContext
import ru.DmN.pht.base.utils.Variable
import ru.DmN.pht.base.utils.VirtualType
import ru.DmN.pht.base.parser.ast.NodeNodesList
import ru.DmN.pht.std.utils.primitiveToObject

object NCList : NodeCompiler<NodeNodesList>() {
    override fun calcType(node: NodeNodesList, compiler: Compiler, ctx: CompilationContext): VirtualType? =
        if (ctx.type.method)
            compiler.typeOf("java.util.List")
        else null

    override fun compile(node: NodeNodesList, compiler: Compiler, ctx: CompilationContext, ret: Boolean): Variable? =
        if (ret && ctx.type.method) {
            ctx.method!!.node.run {
                visitLdcInsn(node.nodes.size)
                visitTypeInsn(Opcodes.ANEWARRAY, "java/lang/Object")
                node.nodes.forEachIndexed { i, it ->
                    visitInsn(Opcodes.DUP)
                    visitLdcInsn(i)
                    primitiveToObject(compiler.compile(it, ctx, true)!!, this)
                    visitInsn(Opcodes.AASTORE)
                }
                visitMethodInsn(Opcodes.INVOKESTATIC, "java/util/Arrays", "asList", "([Ljava/lang/Object;)Ljava/util/List;", false)
            }
            Variable("pht$${node.hashCode()}", "java.util.List", -1, true)
        } else null
}