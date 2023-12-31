package ru.DmN.pht.std.compiler.java.compilers

import org.objectweb.asm.Label
import ru.DmN.pht.std.ast.NodeNamedList
import ru.DmN.pht.std.compiler.java.utils.NamedBlockData
import ru.DmN.pht.std.compiler.java.utils.method
import ru.DmN.pht.std.compiler.java.utils.withNamedBlock
import ru.DmN.siberia.Compiler
import ru.DmN.siberia.compiler.ctx.CompilationContext
import ru.DmN.siberia.compilers.INodeCompiler
import ru.DmN.siberia.compilers.NCDefault
import ru.DmN.siberia.utils.Variable

object NCNamedBlock : INodeCompiler<NodeNamedList> {
    override fun compile(node: NodeNamedList, compiler: Compiler, ctx: CompilationContext) =
        compile(node, ctx) { NCDefault.compile(node, compiler, it) }

    override fun compileVal(node: NodeNamedList, compiler: Compiler, ctx: CompilationContext): Variable =
        compile(node, ctx) { NCDefault.compileVal(node, compiler, it) }

    private fun <T> compile(node: NodeNamedList, ctx: CompilationContext, compile: (ctx: CompilationContext) -> T): T {
        val start = Label()
        val stop = Label()
        ctx.method.node.visitLabel(start)
        val result = compile(ctx.withNamedBlock(node.name, NamedBlockData(start, stop)))
        ctx.method.node.visitLabel(stop)
        return result
    }
}