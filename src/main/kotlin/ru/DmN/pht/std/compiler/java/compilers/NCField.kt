package ru.DmN.pht.std.compiler.java.compilers

import org.objectweb.asm.Label
import org.objectweb.asm.Opcodes
import org.objectweb.asm.tree.FieldNode
import ru.DmN.pht.base.Compiler
import ru.DmN.pht.base.compiler.java.compilers.NodeCompiler
import ru.DmN.pht.base.compiler.java.ctx.CompilationContext
import ru.DmN.pht.base.compiler.java.ctx.FieldContext
import ru.DmN.pht.base.utils.Variable
import ru.DmN.pht.base.utils.VirtualField
import ru.DmN.pht.std.ast.NodeField

object NCField : NodeCompiler<NodeField>() {
    override fun compile(node: NodeField, compiler: Compiler, ctx: CompilationContext, ret: Boolean): Variable? {
        if (ctx.type.method && ctx.type.body) {
            val label = Label()
            ctx.mctx!!.node.visitLabel(label)
            node.fields.forEach { ctx.mctx.createVariable(ctx.bctx!!, it.first, it.second, label) }
        } else if (ctx.type.clazz) {
            val cctx = ctx.cctx!!
            node.fields.forEach {
                val type = ctx.cctx.getType(compiler, ctx.gctx, it.second)
                val fnode = cctx.node.visitField(
                    if (node.static) Opcodes.ACC_PUBLIC + Opcodes.ACC_STATIC else Opcodes.ACC_PUBLIC,
                    it.first,
                    type.desc,
                    cctx.getSignature(type),
                    null
                ) as FieldNode
                val field = VirtualField(it.first, type, node.static, false)
                cctx.clazz.fields += field
                cctx.fields += FieldContext(fnode, field)
            }
        }
        return null
    }
}