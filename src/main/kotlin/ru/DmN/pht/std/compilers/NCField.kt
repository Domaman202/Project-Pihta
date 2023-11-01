package ru.DmN.pht.std.compilers

import org.objectweb.asm.Opcodes
import ru.DmN.pht.base.compiler.java.Compiler
import ru.DmN.pht.base.compiler.java.compilers.INodeCompiler
import ru.DmN.pht.base.compiler.java.utils.CompilationContext
import ru.DmN.pht.std.ast.NodeField
import ru.DmN.pht.std.compiler.java.utils.clazz

object NCField : INodeCompiler<NodeField> {
    override fun compile(node: NodeField, compiler: Compiler, ctx: CompilationContext) {
        val clazz = ctx.clazz.node
        node.fields.forEach { it ->
            clazz.visitField(
                Opcodes.ACC_PUBLIC
                    .let { if (node.static) it + Opcodes.ACC_STATIC else it }
                    .let { if (node.final) it + Opcodes.ACC_FINAL else it },
                it.name,
                it.desc,
                null,
                null
            )
        }
    }
}