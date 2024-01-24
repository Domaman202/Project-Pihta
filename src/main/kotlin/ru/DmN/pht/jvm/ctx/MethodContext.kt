package ru.DmN.pht.std.compiler.java.ctx

import org.objectweb.asm.tree.MethodNode
import ru.DmN.siberia.Compiler
import ru.DmN.siberia.compiler.ctx.CompilationContext
import ru.DmN.siberia.utils.VirtualMethod

class MethodContext(
    val node: MethodNode,
    val method: VirtualMethod,
    val retHook: (Compiler, CompilationContext, MethodContext) -> Unit
) {
    fun returnHook(compiler: Compiler, ctx: CompilationContext) =
        retHook(compiler, ctx, this)
}