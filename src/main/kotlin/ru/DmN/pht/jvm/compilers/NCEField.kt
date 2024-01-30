package ru.DmN.pht.compiler.java.compilers

import org.objectweb.asm.Opcodes
import ru.DmN.siberia.Compiler
import ru.DmN.siberia.compilers.INodeCompiler
import ru.DmN.siberia.compiler.ctx.CompilationContext
import ru.DmN.pht.ast.NodeEField
import ru.DmN.pht.compiler.java.utils.clazz

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