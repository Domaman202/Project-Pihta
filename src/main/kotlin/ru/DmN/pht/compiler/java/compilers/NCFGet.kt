package ru.DmN.pht.std.compiler.java.compilers

import org.objectweb.asm.Opcodes
import ru.DmN.siberia.Compiler
import ru.DmN.siberia.compilers.INodeCompiler
import ru.DmN.siberia.compiler.ctx.CompilationContext
import ru.DmN.siberia.utils.Variable
import ru.DmN.pht.std.ast.NodeFGet
import ru.DmN.pht.std.compiler.java.utils.load
import ru.DmN.pht.std.compiler.java.utils.method

object NCFGet : INodeCompiler<NodeFGet> {
    override fun compileVal(node: NodeFGet, compiler: Compiler, ctx: CompilationContext): Variable =
        ctx.method.node.run {
            when (node.type) {
                NodeFGet.Type.UNKNOWN -> throw UnsupportedOperationException()
                NodeFGet.Type.STATIC -> {
                    val field = node.vtype.fields.asSequence().filter { it.isStatic }.filter { it.name == node.name }.first()
                    visitFieldInsn(Opcodes.GETSTATIC, node.vtype.className, node.name, field.desc)
                    Variable.tmp(node, field.type)
                }

                NodeFGet.Type.INSTANCE -> {
                    val clazz = compiler.compileVal(node.nodes[0], ctx).apply { load(this, this@run) }.type()
                    val field = clazz.fields.asSequence().filter { !it.isStatic }.filter { it.name == node.name }.first()
                    visitFieldInsn(Opcodes.GETFIELD, clazz.className, node.name, field.desc)
                    Variable.tmp(node, field.type)
                }
            }
        }
}