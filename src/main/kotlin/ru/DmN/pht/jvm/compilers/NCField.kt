package ru.DmN.pht.compiler.java.compilers

import org.objectweb.asm.Opcodes
import org.objectweb.asm.tree.FieldNode
import ru.DmN.pht.ast.NodeFieldB
import ru.DmN.pht.compiler.java.utils.classes
import ru.DmN.pht.compiler.java.utils.clazz
import ru.DmN.pht.jvm.compilers.IStdNodeCompiler
import ru.DmN.pht.utils.normalizeName
import ru.DmN.siberia.Compiler
import ru.DmN.siberia.compiler.ctx.CompilationContext

object NCField : IStdNodeCompiler<NodeFieldB, List<FieldNode>> {
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

    override fun getAsm(node: NodeFieldB, compiler: Compiler, ctx: CompilationContext): List<FieldNode> = // todo: тестовый код, потом поправить надо
        node.fields.map { compiler.contexts.classes[it.declaringClass!!.name]!!.fields.find { f -> f.name == it.name }!! }
}