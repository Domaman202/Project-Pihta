package ru.DmN.pht.std.compiler.java.compilers

import org.objectweb.asm.Opcodes
import ru.DmN.siberia.Compiler
import ru.DmN.siberia.compilers.INodeCompiler
import ru.DmN.siberia.compiler.ctx.CompilationContext
import ru.DmN.pht.std.ast.NodeFSet
import ru.DmN.pht.std.compiler.java.utils.load
import ru.DmN.pht.std.compiler.java.utils.method

object NCFSet : INodeCompiler<NodeFSet> {
    override fun compile(node: NodeFSet, compiler: Compiler, ctx: CompilationContext) {
        ctx.method.node.run {
            if (node.field.modifiers.isStatic)
                visitFieldInsn(
                    Opcodes.PUTSTATIC,
                    node.field.declaringClass!!.className,
                    node.field.name,
                    compiler.compileVal(node.nodes[1], ctx).apply { load(this, this@run) }.type().desc
                )
            else {
                val types = node.nodes.map { compiler.compileVal(it, ctx).apply { load(this, this@run) }.type() }
                visitFieldInsn(Opcodes.PUTFIELD, node.field.declaringClass!!.className, node.field.name, types[1].desc)
            }
        }
    }
}