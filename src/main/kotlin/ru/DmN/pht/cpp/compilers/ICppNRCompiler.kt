package ru.DmN.pht.cpp.compilers

import ru.DmN.siberia.ast.Node
import ru.DmN.siberia.compiler.Compiler
import ru.DmN.siberia.compiler.ctx.CompilationContext
import ru.DmN.siberia.utils.Variable
import ru.DmN.siberia.utils.vtype.VirtualType.Companion.VOID

interface ICppNRCompiler<T : Node> : ICppCompiler<T> {
    override fun StringBuilder.compileVal(node: T, compiler: Compiler, ctx: CompilationContext): Variable {
        append("[&]() {")
        compile(node, compiler, ctx)
        append("; return nullptr;}()")
        return Variable.tmp(node, VOID)
    }
}