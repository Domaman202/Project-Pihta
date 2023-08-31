package ru.DmN.pht.std.base.compiler.java.compilers

import org.objectweb.asm.Opcodes
import ru.DmN.pht.base.Compiler
import ru.DmN.pht.base.compiler.java.ctx.CompilationContext
import ru.DmN.pht.base.utils.Variable
import ru.DmN.pht.base.utils.VirtualType
import ru.DmN.pht.std.base.ast.NodeGetOrName
import ru.DmN.pht.std.base.compiler.java.utils.*

object NCGetB : IStdNodeCompiler<NodeGetOrName> {
    override fun calc(node: NodeGetOrName, compiler: Compiler, ctx: CompilationContext): VirtualType? {
        val variable = ctx.body.variables.find { it.name == node.name }
        return if (variable == null)
            if (ctx.isClass())
                ctx.clazz.clazz.fields.find { it.name == node.name }!!.type
            else throw RuntimeException()
        else ctx.global.getType(compiler, variable.type())
    }

    override fun compile(node: NodeGetOrName, compiler: Compiler, ctx: CompilationContext, ret: Boolean): Variable? =
        if (ret)
            ctx.body[node.name] ?: if (ctx.isClass()) {
                var clazz = ctx.clazz.clazz
                var field = clazz.fields.find { it.name == node.name }
                if (field == null) {
                    clazz = ctx.global.getType(compiler, node.name)
                    field = clazz.fields.find { it.name == "INSTANCE" }!!
                }
                ctx.method.node.run {
                    if (field.static)
                        visitFieldInsn(Opcodes.GETSTATIC, clazz.className, field.name, field.desc)
                    else {
                        visitVarInsn(Opcodes.ALOAD, ctx.body["this"]!!.id)
                        visitFieldInsn(Opcodes.GETFIELD, clazz.className, field.name, field.desc)
                    }
                }
                Variable("pht$${node.hashCode()}", field.type.name, -1, true)
            } else throw RuntimeException()
        else null

    override fun compute(node: NodeGetOrName, compiler: Compiler, ctx: CompilationContext, type: ComputeType): Any? =
        if (type == ComputeType.NAME)
            node.name
        else node
}