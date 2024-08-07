package ru.DmN.pht.jvm.compilers

import org.objectweb.asm.Opcodes
import ru.DmN.pht.ast.NodeFGet
import ru.DmN.pht.compiler.java.utils.load
import ru.DmN.pht.jvm.compiler.ctx.method
import ru.DmN.pht.jvm.compilers.IValueNodeCompiler
import ru.DmN.pht.jvm.utils.vtype.desc
import ru.DmN.pht.jvm.utils.vtype.jvmName
import ru.DmN.siberia.compiler.Compiler
import ru.DmN.siberia.compiler.ctx.CompilationContext
import ru.DmN.siberia.utils.Variable

object NCFGet : IValueNodeCompiler<NodeFGet> {
    override fun compileVal(node: NodeFGet, compiler: Compiler, ctx: CompilationContext): Variable =
        ctx.method.node.run {
            when (node.type) {
                NodeFGet.Type.UNKNOWN -> throw UnsupportedOperationException()
                NodeFGet.Type.STATIC -> {
                    val field = node.vtype.fields.asSequence().filter { it.modifiers.isStatic }.filter { it.name == node.name }.first()
                    visitFieldInsn(Opcodes.GETSTATIC, node.vtype.jvmName, node.name, field.desc)
                    Variable.tmp(node, field.type)
                }

                NodeFGet.Type.INSTANCE -> {
                    val clazz = compiler.compileVal(node.nodes[0], ctx).apply { load(this, this@run) }.type
                    val field = clazz.fields.asSequence().filter { !it.modifiers.isStatic }.filter { it.name == node.name }.first()
                    visitFieldInsn(Opcodes.GETFIELD, clazz.jvmName, node.name, field.desc)
                    Variable.tmp(node, field.type)
                }
            }
        }
}