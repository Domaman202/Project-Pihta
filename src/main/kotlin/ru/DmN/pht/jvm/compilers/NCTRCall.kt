package ru.DmN.pht.jvm.compilers

import org.objectweb.asm.Opcodes
import ru.DmN.pht.compiler.java.utils.body
import ru.DmN.pht.compiler.java.utils.load
import ru.DmN.pht.compiler.java.utils.method
import ru.DmN.pht.compiler.java.utils.store
import ru.DmN.siberia.Compiler
import ru.DmN.siberia.ast.NodeNodesList
import ru.DmN.siberia.compiler.ctx.CompilationContext
import ru.DmN.siberia.compilers.INodeCompiler
import ru.DmN.siberia.utils.Variable
import ru.DmN.siberia.utils.VirtualType.Companion.DOUBLE
import ru.DmN.siberia.utils.VirtualType.Companion.LONG

object NCTRCall : INodeCompiler<NodeNodesList> {
    override fun compile(node: NodeNodesList, compiler: Compiler, ctx: CompilationContext) {
        val mctx = ctx.method
        //
        mctx.node.run {
            visitInsn(Opcodes.NOP)
            var i = if (mctx.method.modifiers.static) 0 else 1
            node.nodes
                .asSequence()
                .map { compiler.compileVal(it, ctx) }
                .onEach { load(it, this) }
                .map { it.type() }
                .onEach { if (this == LONG || this == DOUBLE) i += 2 else i++ }
                .toList()
                .forEach {
                    if (this == LONG || this == DOUBLE) i -= 2 else i--
                    store(it.name, i, this)
                }
            visitInsn(Opcodes.NOP)
            visitJumpInsn(Opcodes.GOTO, ctx.body.start)
        }
    }

    override fun compileVal(node: NodeNodesList, compiler: Compiler, ctx: CompilationContext): Variable {
        compile(node, compiler, ctx)
        return Variable.tmp(node, ctx.method.method.rettype)
    }
}