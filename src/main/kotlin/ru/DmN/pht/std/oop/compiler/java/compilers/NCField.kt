package ru.DmN.pht.std.oop.compiler.java.compilers

import org.objectweb.asm.Opcodes
import ru.DmN.pht.base.compiler.java.Compiler
import ru.DmN.pht.base.compiler.java.compilers.INodeCompiler
import ru.DmN.pht.base.compiler.java.utils.CompilationContext
import ru.DmN.pht.base.utils.desc
import ru.DmN.pht.std.base.compiler.java.utils.clazz
import ru.DmN.pht.std.oop.ast.NodeField

object NCField : INodeCompiler<NodeField> {
    override fun compile(node: NodeField, compiler: Compiler, ctx: CompilationContext) {
        val clazz = ctx.clazz.node
        node.fields.forEach { it ->
            clazz.visitField(
                Opcodes.ACC_PUBLIC
                    .let { if (node.static) it + Opcodes.ACC_STATIC else it }
                    .let { if (node.final) it + Opcodes.ACC_FINAL else it },
                it.second,
                it.first.desc,
                null,
                null
            )
        }
    }
}