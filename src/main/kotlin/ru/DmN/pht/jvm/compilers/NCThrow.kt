package ru.DmN.pht.compiler.java.compilers

import org.objectweb.asm.Opcodes
import ru.DmN.pht.compiler.java.utils.load
import ru.DmN.pht.jvm.compiler.ctx.method
import ru.DmN.siberia.ast.NodeNodesList
import ru.DmN.siberia.compiler.Compiler
import ru.DmN.siberia.compiler.ctx.CompilationContext
import ru.DmN.siberia.compilers.INodeCompiler
import ru.DmN.siberia.utils.Variable

object NCThrow : INodeCompiler<NodeNodesList> {
    override fun compileVal(node: NodeNodesList, compiler: Compiler, ctx: CompilationContext): Variable {
        compile(node, compiler, ctx)
        return Variable.tmp(node, null)
    }

    override fun compile(node: NodeNodesList, compiler: Compiler, ctx: CompilationContext) {
        ctx.method.node.run {
            load(compiler.compileVal(node.nodes[0], ctx), this)
            visitInsn(Opcodes.ATHROW)
        }
    }
}