package ru.DmN.pht.compiler.java.compilers

import org.objectweb.asm.Opcodes
import org.objectweb.asm.tree.FieldNode
import ru.DmN.pht.ast.NodeFieldB
import ru.DmN.pht.jvm.compiler.ctx.clazz
import ru.DmN.pht.jvm.compilers.IStdNodeCompiler
import ru.DmN.pht.utils.normalizeName
import ru.DmN.siberia.compiler.Compiler
import ru.DmN.siberia.compiler.ctx.CompilationContext

object NCField : IStdNodeCompiler<NodeFieldB, List<FieldNode>, Nothing> {
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

    override fun compileAsm(node: NodeFieldB, compiler: Compiler, ctx: CompilationContext): List<FieldNode> {
        val clazz = ctx.clazz.node
        return node.fields.map { it ->
            clazz.visitField(
                Opcodes.ACC_PUBLIC
                    .let { if (node.static) it + Opcodes.ACC_STATIC else it }
                    .let { if (node.final) it + Opcodes.ACC_FINAL else it },
                it.name.normalizeName(),
                it.desc,
                null,
                null
            ) as FieldNode
        }
    }
}