package ru.DmN.pht.std.compiler.java.compilers

import ru.DmN.pht.base.compiler.java.Compiler
import ru.DmN.pht.base.compiler.java.compilers.NodeCompiler
import ru.DmN.pht.base.compiler.java.ctx.CompilationContext
import ru.DmN.pht.base.utils.Variable
import ru.DmN.pht.std.ast.NodeDefMacro

object NCDefMacro : NodeCompiler<NodeDefMacro>() {
    override fun compile(node: NodeDefMacro, compiler: Compiler, ctx: CompilationContext, ret: Boolean): Variable? {
        if (ctx.type == CompilationContext.Type.GLOBAL)
            ctx.global.macros += node
        return null
    }
}