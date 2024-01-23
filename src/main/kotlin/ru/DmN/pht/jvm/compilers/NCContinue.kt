package ru.DmN.pht.std.compiler.java.compilers

import org.objectweb.asm.Opcodes
import ru.DmN.pht.std.ast.NodeNamedList
import ru.DmN.pht.std.compiler.java.utils.getNamedBlock
import ru.DmN.pht.std.compiler.java.utils.method
import ru.DmN.siberia.Compiler
import ru.DmN.siberia.compiler.ctx.CompilationContext
import ru.DmN.siberia.compilers.INodeCompiler

object NCContinue : INodeCompiler<NodeNamedList> {
    override fun compile(node: NodeNamedList, compiler: Compiler, ctx: CompilationContext) {
        ctx.method.node.visitJumpInsn(Opcodes.GOTO, ctx.getNamedBlock(node.name).start)
    }
}