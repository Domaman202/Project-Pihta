package ru.DmN.pht.std.compiler.java.compilers

import org.objectweb.asm.Label
import org.objectweb.asm.Opcodes
import ru.DmN.pht.base.ast.NodeNodesList
import ru.DmN.pht.base.compiler.java.Compiler
import ru.DmN.pht.base.compiler.java.compilers.INodeCompiler
import ru.DmN.pht.base.compiler.java.utils.CompilationContext
import ru.DmN.pht.std.compiler.java.utils.load
import ru.DmN.pht.std.compiler.java.utils.method

object NCCycle : INodeCompiler<NodeNodesList> {
    override fun compile(node: NodeNodesList, compiler: Compiler, ctx: CompilationContext) {
        ctx.method.node.run {
            val start = Label()
            visitLabel(start)
            load(compiler.compileVal(node.nodes[0], ctx), this)
            val stop = Label()
            visitJumpInsn(Opcodes.IFEQ, stop)
            node.nodes.drop(1).forEach { compiler.compile(it, ctx) }
            visitJumpInsn(Opcodes.GOTO, start)
            visitLabel(stop)
        }
    }
}