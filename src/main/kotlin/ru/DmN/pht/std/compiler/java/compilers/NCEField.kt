package ru.DmN.pht.std.compiler.java.compilers

import org.objectweb.asm.Opcodes
import ru.DmN.siberia.compiler.java.Compiler
import ru.DmN.siberia.compiler.java.compilers.INodeCompiler
import ru.DmN.siberia.compiler.java.utils.CompilationContext
import ru.DmN.pht.std.ast.NodeEField
import ru.DmN.pht.std.compiler.java.utils.clazz

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