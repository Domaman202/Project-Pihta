package ru.DmN.pht.std.compiler.java.compilers

import org.objectweb.asm.Opcodes
import ru.DmN.siberia.Compiler
import ru.DmN.siberia.compilers.INodeCompiler
import ru.DmN.siberia.compiler.ctx.CompilationContext
import ru.DmN.siberia.utils.Variable
import ru.DmN.pht.std.compiler.java.utils.body
import ru.DmN.pht.std.compiler.java.utils.clazz
import ru.DmN.pht.std.compiler.java.utils.method
import ru.DmN.pht.std.ast.NodeGetA
import ru.DmN.pht.std.ast.NodeGetB
import ru.DmN.pht.std.processor.utils.classes

object NCGetA : INodeCompiler<NodeGetB> {
    override fun compileVal(node: NodeGetB, compiler: Compiler, ctx: CompilationContext): Variable =
        when (node.type) {
            NodeGetA.Type.VARIABLE -> ctx.body[node.name]!!
            NodeGetA.Type.THIS_FIELD, NodeGetA.Type.THIS_STATIC_FIELD -> ctx.method.node.run {
                val field = ctx.clazz.clazz.fields.find { it.name == name } ?: ctx.classes.asSequence().map { it -> it.fields.find { it.name == node.name } }.first()!!
                visitFieldInsn(
                    if (node.type == NodeGetA.Type.THIS_FIELD) Opcodes.GETFIELD else Opcodes.GETSTATIC,
                    field.declaringClass!!.className,
                    node.name,
                    field.desc
                )
                Variable(node.name, field.type, -1, true)
            }
        }
}