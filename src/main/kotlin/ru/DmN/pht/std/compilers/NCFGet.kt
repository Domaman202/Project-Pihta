package ru.DmN.pht.std.compilers

import org.objectweb.asm.Opcodes
import ru.DmN.pht.base.compiler.java.Compiler
import ru.DmN.pht.base.compiler.java.compilers.INodeCompiler
import ru.DmN.pht.base.compiler.java.utils.CompilationContext
import ru.DmN.pht.base.utils.Variable
import ru.DmN.pht.std.ast.NodeFGet
import ru.DmN.pht.std.compiler.java.utils.load
import ru.DmN.pht.std.compiler.java.utils.method

object NCFGet : INodeCompiler<NodeFGet> {
    override fun compileVal(node: NodeFGet, compiler: Compiler, ctx: CompilationContext): Variable =
        ctx.method.node.run {
            when (node.type) {
                NodeFGet.Type.UNKNOWN -> throw UnsupportedOperationException()
                NodeFGet.Type.STATIC -> {
                    TODO("Need Impl")
                }

                NodeFGet.Type.INSTANCE -> {
                    val clazz = compiler.compileVal(node.nodes[0], ctx).apply { load(this, this@run) }.type()
                    val field = clazz.fields.asSequence().filter { !it.static }.first()
                    visitFieldInsn(Opcodes.GETFIELD, clazz.className, node.name, field.desc)
                    Variable.tmp(node, field.type)
                }
            }
        }
}