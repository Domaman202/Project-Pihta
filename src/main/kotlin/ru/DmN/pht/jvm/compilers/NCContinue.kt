package ru.DmN.pht.jvm.compilers

import org.objectweb.asm.Opcodes
import ru.DmN.pht.ast.NodeNamedList
import ru.DmN.pht.jvm.compiler.ctx.getNamedBlock
import ru.DmN.pht.jvm.compiler.ctx.method
import ru.DmN.siberia.compiler.Compiler
import ru.DmN.siberia.compiler.ctx.CompilationContext
import ru.DmN.siberia.compilers.INodeCompiler

object NCContinue : INodeCompiler<NodeNamedList> {
    override fun compile(node: NodeNamedList, compiler: Compiler, ctx: CompilationContext) {
        ctx.method.node.visitJumpInsn(Opcodes.GOTO, ctx.getNamedBlock(node.name).start)
    }
}