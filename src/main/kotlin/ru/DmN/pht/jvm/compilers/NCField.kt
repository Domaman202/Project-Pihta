package ru.DmN.pht.std.compiler.java.compilers

import org.objectweb.asm.Opcodes
import ru.DmN.siberia.Compiler
import ru.DmN.siberia.compilers.INodeCompiler
import ru.DmN.siberia.compiler.ctx.CompilationContext
import ru.DmN.pht.std.ast.NodeFieldB
import ru.DmN.pht.std.compiler.java.utils.clazz
import ru.DmN.pht.std.utils.normalizeName

object NCField : INodeCompiler<NodeFieldB> {
    override fun compile(node: NodeFieldB, compiler: Compiler, ctx: CompilationContext) {
        val clazz = ctx.clazz.node
        node.fields.forEach { it ->
            clazz.visitField(
                Opcodes.ACC_PUBLIC
                    .let { if (node.static) it + Opcodes.ACC_STATIC else it }
                    .let { if (node.final) it + Opcodes.ACC_FINAL else it },
                it.name.normalizeName(),
                it.desc,
                null,
                null
            )
        }
    }
}