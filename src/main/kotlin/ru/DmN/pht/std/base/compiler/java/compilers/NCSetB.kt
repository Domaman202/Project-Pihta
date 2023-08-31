package ru.DmN.pht.std.base.compiler.java.compilers

import org.objectweb.asm.Opcodes
import ru.DmN.pht.base.Compiler
import ru.DmN.pht.base.compiler.java.ctx.CompilationContext
import ru.DmN.pht.base.utils.Variable
import ru.DmN.pht.base.utils.VirtualType
import ru.DmN.pht.std.base.ast.NodeSet
import ru.DmN.pht.std.base.utils.calcType
import ru.DmN.pht.std.base.compiler.java.utils.*
import ru.DmN.pht.std.base.utils.load
import ru.DmN.pht.std.base.utils.store
import ru.DmN.pht.std.base.utils.storeCast

object NCSetB : IStdNodeCompiler<NodeSet> {
    override fun calc(node: NodeSet, compiler: Compiler, ctx: CompilationContext): VirtualType? =
        compiler.calc(node.value!!, ctx)

    override fun compile(node: NodeSet, compiler: Compiler, ctx: CompilationContext, ret: Boolean): Variable? {
        val mctx = ctx.method
        var valueType: String? = null
        val value = { flag: Boolean ->
            valueType = compiler.compile(node.value!!, ctx, true)!!.apply { load(this, mctx.node) }.type
            mctx.node.visitInsn(if (flag) Opcodes.DUP_X1 else Opcodes.DUP)
        }
        val variable = ctx.body[node.name]
        if (variable == null) {
            if (ctx.isClass()) {
                val cctx = ctx.clazz
                cctx.fields.find { it.field.name == node.name }.let {
                    mctx.node.run {
                        val field = it!!.field
                        if (field.static) {
                            value(false)
                            visitFieldInsn(Opcodes.PUTSTATIC, cctx.node.name, node.name, field.desc)
                        } else {
                            visitVarInsn(Opcodes.ALOAD, ctx.body["this"]!!.id)
                            value(true)
                            visitFieldInsn(Opcodes.PUTFIELD, cctx.node.name, node.name, field.desc)
                        }
                    }
                }
            } else throw RuntimeException()
        } else {
            val gctx = ctx.global
            value(false)
            val result = calcType(
                variable.type?.let { gctx.getType(compiler, it) },
                valueType?.let { gctx.getType(compiler, it) }
            )
            variable.type = result.first?.name
            if (result.second == null)
                store(variable, mctx.node)
            else storeCast(variable, result.second!!, mctx.node)
        }
        return if (ret)
            Variable("tmp$${node.hashCode()}", valueType, -1, true)
        else null
    }
}