package ru.DmN.pht.std.compiler.java.compilers

import org.objectweb.asm.Opcodes
import ru.DmN.pht.base.compiler.java.Compiler
import ru.DmN.pht.base.compiler.java.compilers.NodeCompiler
import ru.DmN.pht.base.compiler.java.ctx.CompilationContext
import ru.DmN.pht.base.utils.Variable
import ru.DmN.pht.base.utils.VirtualType
import ru.DmN.pht.std.ast.NodeSet
import ru.DmN.pht.std.utils.load
import ru.DmN.pht.std.utils.store
import ru.DmN.pht.std.utils.storeCast

object NCSetB : NodeCompiler<NodeSet>() {
    override fun calc(node: NodeSet, compiler: Compiler, ctx: CompilationContext): VirtualType? =
        if (ctx.type.method && ctx.type.body)
            compiler.calc(node.value!!, ctx)
        else null

    override fun compile(node: NodeSet, compiler: Compiler, ctx: CompilationContext, ret: Boolean): Variable? =
        if (ctx.type.method && ctx.type.body) {
            val mctx = ctx.method!!
            var valueType: String? = null
            val value = { flag: Boolean ->
                valueType = compiler.compile(node.value!!, ctx, true)!!.apply { load(this, mctx.node) }.type
                mctx.node.visitInsn(if (flag) Opcodes.DUP_X1 else Opcodes.DUP)
            }
            val variable = ctx.body!![node.name]
            if (variable == null) {
                if (ctx.type.clazz) {
                    ctx.clazz!!.fields.find { it.field.name == node.name }.let {
                        mctx.node.run {
                            val field = it!!.field
                            if (field.static) {
                                value(false)
                                visitFieldInsn(Opcodes.PUTSTATIC, ctx.clazz.node.name, node.name, field.desc)
                            } else {
                                visitVarInsn(Opcodes.ALOAD, ctx.body["this"]!!.id)
                                value(true)
                                visitFieldInsn(Opcodes.PUTFIELD, ctx.clazz.node.name, node.name, field.desc)
                            }
                        }
                    }
                } else throw RuntimeException()
            } else {
                value(false)
                val result = ru.DmN.pht.std.utils.calcType(
                    variable.type?.let { ctx.global.getType(compiler, it) },
                    valueType?.let { ctx.global.getType(compiler, it) }
                )
                variable.type = result.first?.name
                if (result.second == null)
                    store(variable, mctx.node)
                else storeCast(variable, result.second!!, mctx.node)
            }
            if (ret)
                Variable("tmp$${node.hashCode()}", valueType, -1, true)
            else null
        } else null
}