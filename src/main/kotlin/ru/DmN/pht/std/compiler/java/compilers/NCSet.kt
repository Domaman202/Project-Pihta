package ru.DmN.pht.std.compiler.java.compilers

import org.objectweb.asm.Opcodes
import ru.DmN.pht.base.compiler.java.Compiler
import ru.DmN.pht.base.compiler.java.compilers.INodeCompiler
import ru.DmN.pht.base.compiler.java.utils.CompilationContext
import ru.DmN.pht.std.ast.NodeSet
import ru.DmN.pht.std.compiler.java.utils.*

object NCSet : INodeCompiler<NodeSet> {
    override fun compile(node: NodeSet, compiler: Compiler, ctx: CompilationContext) {
        ctx.method.node.run {
            val value = compiler.compileVal(node.nodes.first(), ctx)
            load(value, this)
            ctx.body[node.name]?.let { storeCast(it, value.type(), this) }
                ?: ctx.clazz.clazz.fields.find { it.name == node.name }!!.run {
                    visitFieldInsn(
                        if (static) Opcodes.PUTSTATIC else Opcodes.PUTFIELD,
                        declaringClass!!.className,
                        name,
                        desc
                    )
                }
        }
    }
}