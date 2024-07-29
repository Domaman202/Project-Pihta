package ru.DmN.pht.jvm.compilers

import org.objectweb.asm.Opcodes
import ru.DmN.pht.ast.NodeEField
import ru.DmN.pht.jvm.compiler.ctx.clazz
import ru.DmN.pht.jvm.utils.vtype.desc
import ru.DmN.siberia.compiler.Compiler
import ru.DmN.siberia.compiler.ctx.CompilationContext
import ru.DmN.siberia.compilers.INodeCompiler

object NCEField : INodeCompiler<NodeEField> {
    override fun compile(node: NodeEField, compiler: Compiler, ctx: CompilationContext) {
        val clazz = ctx.clazz
        clazz.node.run {
            node.fields.forEach {
                visitField(
                    Opcodes.ACC_PUBLIC + Opcodes.ACC_ENUM + Opcodes.ACC_FINAL + Opcodes.ACC_STATIC,
                    it.first,
                    clazz.clazz.desc,
                    null,
                    null
                )
            }
        }
    }
}