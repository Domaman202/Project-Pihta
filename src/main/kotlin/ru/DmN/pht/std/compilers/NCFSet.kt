package ru.DmN.pht.std.compilers

import org.objectweb.asm.Opcodes
import ru.DmN.pht.base.compiler.java.Compiler
import ru.DmN.pht.base.compiler.java.compilers.INodeCompiler
import ru.DmN.pht.base.compiler.java.utils.CompilationContext
import ru.DmN.pht.std.ast.NodeFSet
import ru.DmN.pht.std.compiler.java.utils.load
import ru.DmN.pht.std.compiler.java.utils.method

object NCFSet : INodeCompiler<NodeFSet> {
    override fun compile(node: NodeFSet, compiler: Compiler, ctx: CompilationContext) {
        ctx.method.node.run {
            when (node.type) {
                NodeFSet.Type.UNKNOWN -> throw UnsupportedOperationException()
                NodeFSet.Type.STATIC -> {
                    TODO("Need Impl")
                }

                NodeFSet.Type.INSTANCE -> {
                    val types = node.nodes.map { compiler.compileVal(it, ctx).apply { load(this, this@run) }.type() }
                    visitFieldInsn(Opcodes.PUTFIELD, types[0].className, node.name, types[1].desc)
                }
            }
        }
    }
}