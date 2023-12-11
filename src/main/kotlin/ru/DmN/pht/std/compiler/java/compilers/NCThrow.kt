package ru.DmN.pht.std.compiler.java.compilers

import jdk.internal.org.objectweb.asm.Opcodes
import ru.DmN.pht.std.compiler.java.utils.load
import ru.DmN.pht.std.compiler.java.utils.method
import ru.DmN.siberia.Compiler
import ru.DmN.siberia.ast.NodeNodesList
import ru.DmN.siberia.compiler.ctx.CompilationContext
import ru.DmN.siberia.compilers.INodeCompiler

object NCThrow : INodeCompiler<NodeNodesList> {
    override fun compile(node: NodeNodesList, compiler: Compiler, ctx: CompilationContext) {
        ctx.method.node.run {
            load(compiler.compileVal(node.nodes[0], ctx), this)
            visitInsn(Opcodes.ATHROW)
        }
    }
}