package ru.DmN.pht.compiler.java.compilers

import org.objectweb.asm.Opcodes
import ru.DmN.pht.ast.NodeFSet
import ru.DmN.pht.compiler.java.utils.load
import ru.DmN.pht.jvm.compiler.ctx.method
import ru.DmN.pht.jvm.utils.vtype.desc
import ru.DmN.pht.jvm.utils.vtype.jvmName
import ru.DmN.siberia.compiler.Compiler
import ru.DmN.siberia.compiler.ctx.CompilationContext
import ru.DmN.siberia.compilers.INodeCompiler

object NCFSet : INodeCompiler<NodeFSet> {
    override fun compile(node: NodeFSet, compiler: Compiler, ctx: CompilationContext) {
        ctx.method.node.run {
            if (node.field.modifiers.isStatic)
                visitFieldInsn(
                    Opcodes.PUTSTATIC,
                    node.field.declaringClass.jvmName,
                    node.field.name,
                    compiler.compileVal(node.nodes[1], ctx).apply { load(this, this@run) }.type().desc
                )
            else {
                val types = node.nodes.map { compiler.compileVal(it, ctx).apply { load(this, this@run) }.type() }
                visitFieldInsn(Opcodes.PUTFIELD, node.field.declaringClass.jvmName, node.field.name, types[1].desc)
            }
        }
    }
}