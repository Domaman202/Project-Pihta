package ru.DmN.pht.base.compiler.java.compilers

import ru.DmN.pht.base.compiler.java.Compiler
import ru.DmN.pht.base.compiler.java.ctx.CompilationContext
import ru.DmN.pht.base.utils.Variable
import ru.DmN.pht.base.utils.VirtualType
import ru.DmN.pht.base.parser.ast.Node

interface INodeCompiler<T : Node> {
    fun calc(node: T, compiler: Compiler, ctx: CompilationContext): VirtualType? = null
    fun compile(node: T, compiler: Compiler, ctx: CompilationContext, ret: Boolean): Variable? = null
}