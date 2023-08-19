package ru.DmN.pht.std.compiler.java.compilers

import org.objectweb.asm.Opcodes
import ru.DmN.pht.base.compiler.java.Compiler
import ru.DmN.pht.base.compiler.java.ctx.CompilationContext
import ru.DmN.pht.base.utils.Variable
import ru.DmN.pht.base.utils.VirtualType
import ru.DmN.pht.std.ast.NodeFMGet
import ru.DmN.pht.std.compiler.java.global
import ru.DmN.pht.std.compiler.java.method
import ru.DmN.pht.std.utils.load

object NCFieldGetB : IStdNodeCompiler<NodeFMGet> {
    override fun calc(node: NodeFMGet, compiler: Compiler, ctx: CompilationContext): VirtualType? =
        compiler.calc(node.instance, ctx)!!.fields.find { it.name == node.name && it.static == node.static }!!.type

    override fun compile(node: NodeFMGet, compiler: Compiler, ctx: CompilationContext, ret: Boolean): Variable? =
        if (ret)
            ctx.method.node.run {
                val instanceType = ctx.global.getType(
                    compiler,
                    if (node.static)
                        node.instance.getConstValueAsString()
                    else compiler.compile(node.instance, ctx, true)!!.apply { load(this@apply, this@run) }.type()
                )
                val field = instanceType.fields.find { it.name == node.name && it.static == node.static }!!
                visitFieldInsn(
                    if (field.static) Opcodes.GETSTATIC else Opcodes.GETFIELD,
                    instanceType.className,
                    node.name,
                    field.desc
                )
                Variable("lul$${node.hashCode()}", field.type.name, -1, true)
            }
        else null
}