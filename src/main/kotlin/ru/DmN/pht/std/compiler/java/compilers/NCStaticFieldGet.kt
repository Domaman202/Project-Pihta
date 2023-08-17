package ru.DmN.pht.std.compiler.java.compilers

import org.objectweb.asm.Opcodes
import ru.DmN.pht.base.compiler.java.Compiler
import ru.DmN.pht.base.compiler.java.compilers.NodeCompiler
import ru.DmN.pht.base.compiler.java.ctx.CompilationContext
import ru.DmN.pht.base.utils.Variable
import ru.DmN.pht.base.utils.VirtualType
import ru.DmN.pht.std.ast.NodeFMGet

object NCStaticFieldGet : NodeCompiler<NodeFMGet>() {
    override fun calc(node: NodeFMGet, compiler: Compiler, ctx: CompilationContext): VirtualType? =
        if (ctx.type.method)
            ctx.global.getType(compiler, node.instance.getValueAsString()).fields.find { it.name == node.name }!!.type
        else null

    override fun compile(node: NodeFMGet, compiler: Compiler, ctx: CompilationContext, ret: Boolean): Variable? =
        if (ret && ctx.type.method) {
            val clazz = ctx.global.getType(compiler, node.instance.getValueAsString())
            val field = clazz.fields.find { it.name == node.name }!!
            ctx.method!!.node.visitFieldInsn(Opcodes.GETSTATIC, clazz.name.replace('.', '/'), node.name, field.desc)
            Variable(node.name, field.name, -1, true)
        } else null
}