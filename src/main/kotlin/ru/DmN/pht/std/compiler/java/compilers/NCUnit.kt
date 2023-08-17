package ru.DmN.pht.std.compiler.java.compilers

import org.objectweb.asm.Opcodes
import ru.DmN.pht.base.compiler.java.Compiler
import ru.DmN.pht.base.compiler.java.compilers.NodeCompiler
import ru.DmN.pht.base.compiler.java.ctx.CompilationContext
import ru.DmN.pht.base.parser.ast.Node
import ru.DmN.pht.base.utils.Variable
import ru.DmN.pht.base.utils.VirtualType

object NCUnit : NodeCompiler<Node>() {
    override fun calc(node: Node, compiler: Compiler, ctx: CompilationContext): VirtualType? =
        if (ctx.type.method)
            VirtualType.ofKlass(Unit::class.java)
        else null

    override fun compile(node: Node, compiler: Compiler, ctx: CompilationContext, ret: Boolean): Variable? =
        if (ret && ctx.type.method) {
            ctx.method!!.node.visitFieldInsn(Opcodes.GETSTATIC, "kotlin/Unit", "INSTANCE", "Lkotlin/Unit;")
            Variable("lul$${node.hashCode()}", "kotlin.Unit", -1, true)
        } else null
}