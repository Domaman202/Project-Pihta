package ru.DmN.pht.std.base.compiler.java.compilers

import org.objectweb.asm.Opcodes
import ru.DmN.pht.base.compiler.java.Compiler
import ru.DmN.pht.base.compiler.java.ctx.CompilationContext
import ru.DmN.pht.base.utils.Variable
import ru.DmN.pht.base.utils.VirtualType
import ru.DmN.pht.std.base.ast.NodeFMGet
import ru.DmN.pht.std.base.compiler.java.utils.ComputeType
import ru.DmN.pht.std.base.compiler.java.utils.computeName
import ru.DmN.pht.std.base.compiler.java.utils.global
import ru.DmN.pht.std.base.compiler.java.utils.method
import ru.DmN.pht.std.base.utils.load

object NCFieldGetB : IStdNodeCompiler<NodeFMGet> {
    override fun calc(node: NodeFMGet, compiler: Compiler, ctx: CompilationContext): VirtualType =
        (if (node.static) ctx.global.getType(compiler, node.instance.getConstValueAsString()) else compiler.calc(node.instance, ctx)!!)
            .fields.find { it.name == node.name && it.static == node.static }!!.type

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
                Variable("pht$${node.hashCode()}", field.type.name, -1, true)
            }
        else null

    override fun compute(node: NodeFMGet, compiler: Compiler, ctx: CompilationContext, type: ComputeType): Any? =
        if (type == ComputeType.NAME)
            "${compiler.computeName(node.instance, ctx)}/${node.name}"
        else node
}