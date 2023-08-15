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

object NCSet : NodeCompiler<NodeSet>() {
    override fun calcType(node: NodeSet, compiler: Compiler, ctx: CompilationContext): VirtualType? =
        if (ctx.type.method && ctx.type.body)
            compiler.calc(node.value!!, ctx)
        else null

    override fun compile(node: NodeSet, compiler: Compiler, ctx: CompilationContext, ret: Boolean): Variable? =
        if (ctx.type.method && ctx.type.body) {
            val mctx = ctx.method!!
            var valueType: String? = null
            val value = { valueType = compiler.compile(node.value!!, ctx, true)!!.apply { load(this, mctx.node) }.type }
            if (ret)
                mctx.node.visitInsn(Opcodes.DUP)
            val variable = ctx.body!![node.name]
            if (variable == null) {
                if (ctx.type.clazz) {
                    val cctx = ctx.clazz!!
                    cctx.fields.find { it.field.name == node.name }.let {
                        mctx.node.run {
                            val field = it!!.field
                            if (field.static) {
                                value()
                                visitFieldInsn(Opcodes.PUTSTATIC, cctx.node.name, node.name, field.desc)
                            } else {
                                visitVarInsn(Opcodes.ALOAD, ctx.body["this"]!!.id)
                                value()
                                visitFieldInsn(Opcodes.PUTFIELD, cctx.node.name, node.name, field.desc)
                            }
                        }
                    }
                } else throw RuntimeException()
            } else {
                value()
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