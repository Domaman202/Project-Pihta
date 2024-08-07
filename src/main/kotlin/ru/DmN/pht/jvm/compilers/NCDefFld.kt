package ru.DmN.pht.jvm.compilers

import org.objectweb.asm.Opcodes
import ru.DmN.pht.ast.NodeFieldB
import ru.DmN.pht.jvm.compiler.ctx.clazz
import ru.DmN.pht.jvm.utils.vtype.desc
import ru.DmN.siberia.compiler.Compiler
import ru.DmN.siberia.compiler.ctx.CompilationContext
import ru.DmN.siberia.compilers.INodeCompiler

object NCDefFld : INodeCompiler<NodeFieldB> {
    override fun compile(node: NodeFieldB, compiler: Compiler, ctx: CompilationContext) {
        val clazz = ctx.clazz.node
        node.fields.forEach { it ->
            clazz.visitField(
                Opcodes.ACC_PUBLIC
                    .let { if (node.static) it + Opcodes.ACC_STATIC else it }
                    .let { if (node.final) it + Opcodes.ACC_FINAL else it },
                it.name,
                it.type.desc,
                null,
                null
            )
        }
    }
}