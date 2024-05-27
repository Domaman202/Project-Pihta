package ru.DmN.pht.jvm.compilers

import org.objectweb.asm.Opcodes
import ru.DmN.pht.jvm.compiler.ctx.method
import ru.DmN.siberia.ast.Node
import ru.DmN.siberia.compiler.Compiler
import ru.DmN.siberia.compiler.ctx.CompilationContext
import ru.DmN.siberia.compilers.INodeCompiler

interface IValueNodeCompiler<T : Node> : INodeCompiler<T> {
    override fun compile(node: T, compiler: Compiler, ctx: CompilationContext) {
        this.compileVal(node, compiler, ctx)
        ctx.method.node.visitInsn(Opcodes.POP)
    }
}