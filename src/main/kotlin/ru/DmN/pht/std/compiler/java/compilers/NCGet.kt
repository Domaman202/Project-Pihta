package ru.DmN.pht.std.compiler.java.compilers

import org.objectweb.asm.Opcodes
import ru.DmN.pht.base.compiler.java.Compiler
import ru.DmN.pht.base.compiler.java.compilers.NodeCompiler
import ru.DmN.pht.base.compiler.java.ctx.CompilationContext
import ru.DmN.pht.base.utils.Variable
import ru.DmN.pht.base.utils.VirtualType
import ru.DmN.pht.std.ast.NodeGet

object NCGet : NodeCompiler<NodeGet>() {
    override fun calcType(node: NodeGet, compiler: Compiler, ctx: CompilationContext): VirtualType? =
        if (ctx.type.method && ctx.type.body) {
            ctx.bctx!![node.name]?.type?.let {
                return if (ctx.type.clazz)
                    ctx.cctx!!.getType(compiler, ctx.gctx, it)
                else ctx.gctx.getTypeOrNull(compiler, it)
            }
            if (ctx.type.clazz)
                ctx.cctx!!.fields.find { it.field.name == node.name }?.let { return it.field.type }
            ctx.gctx.getType(compiler, node.name)
        } else null

    override fun compile(node: NodeGet, compiler: Compiler, ctx: CompilationContext, ret: Boolean): Variable? =
        if (ret && ctx.type.method && ctx.type.body) {
            val mctx = ctx.mctx!!
            ctx.bctx!![node.name]?.let { return it }
            if (ctx.type.clazz) {
                val cctx = ctx.cctx!!
                cctx.fields.find { it.field.name == node.name }?.let { it ->
                    mctx.node.run {
                        val field = it.field
                        if (field.static)
                            visitFieldInsn(Opcodes.GETSTATIC, cctx.node.name, field.name, field.desc)
                        else {
                            visitVarInsn(Opcodes.ALOAD, ctx.bctx["this"]!!.id)
                            visitFieldInsn(Opcodes.GETFIELD, cctx.node.name, field.name, field.desc)
                        }
                        return Variable(field.name, field.type.name, -1, true)
                    }
                }
            }
            ctx.gctx.getTypeOrNull(compiler, node.name)?.let { it ->
                val instance = it.fields.find { it.name == "INSTANCE" }
                if (instance == null)
                    null
                else {
                    mctx.node.visitFieldInsn(Opcodes.GETSTATIC, it.className, "INSTANCE", it.desc)
                    Variable("pht$${node.hashCode()}", it.name, -1, true)
                }
            }
        } else null
}