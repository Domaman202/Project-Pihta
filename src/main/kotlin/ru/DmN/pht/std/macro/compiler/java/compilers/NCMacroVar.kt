package ru.DmN.pht.std.macro.compiler.java.compilers

import ru.DmN.pht.base.compiler.java.Compiler
import ru.DmN.pht.base.compiler.java.compilers.NCDefault
import ru.DmN.pht.base.compiler.java.ctx.CompilationContext
import ru.DmN.pht.base.utils.Variable
import ru.DmN.pht.base.utils.VirtualType
import ru.DmN.pht.std.base.compiler.java.compilers.IStdNodeCompiler
import ru.DmN.pht.std.macro.ast.NodeMacroVar
import ru.DmN.pht.std.base.compiler.java.utils.computeNL
import ru.DmN.pht.std.base.compiler.java.utils.computeName
import ru.DmN.pht.std.base.compiler.java.utils.macro

object NCMacroVar : IStdNodeCompiler<NodeMacroVar> {
    override fun calc(node: NodeMacroVar, compiler: Compiler, ctx: CompilationContext): VirtualType? {
        process(node, compiler, ctx)
        return NCDefault.calc(node, compiler, ctx)
    }

    override fun compile(node: NodeMacroVar, compiler: Compiler, ctx: CompilationContext, ret: Boolean): Variable? {
        process(node, compiler, ctx)
        return NCDefault.compile(node, compiler, ctx, ret)
    }

    fun process(node: NodeMacroVar, compiler: Compiler, ctx: CompilationContext) {
        val pair = compiler.computeNL(node.nodes.first(), ctx)
        ctx.macro["${compiler.computeName(pair.first(), ctx)}$${node.name}"] = pair.last()
    }
}