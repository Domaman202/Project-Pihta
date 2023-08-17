package ru.DmN.pht.base.compiler.java.compilers

import ru.DmN.pht.base.compiler.java.Compiler
import ru.DmN.pht.base.compiler.java.ctx.CompilationContext
import ru.DmN.pht.base.utils.Variable
import ru.DmN.pht.base.utils.VirtualType
import ru.DmN.pht.base.parser.ast.Node

open class NodeCompiler<T : Node> {
    open fun calc(node: T, compiler: Compiler, ctx: CompilationContext): VirtualType? = null
    open fun compile(node: T, compiler: Compiler, ctx: CompilationContext, ret: Boolean): Variable? = null
    open fun compute(node: T, compiler: Compiler, ctx: CompilationContext, name: Boolean): Any? = node
    open fun applyAnnotation(node: T, compiler: Compiler, ctx: CompilationContext, annotation: Node) = Unit

    companion object {
        val INSTANCE = NodeCompiler<Node>()
    }
}