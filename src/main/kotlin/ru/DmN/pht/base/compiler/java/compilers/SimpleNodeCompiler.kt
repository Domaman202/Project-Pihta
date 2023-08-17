package ru.DmN.pht.base.compiler.java.compilers

import ru.DmN.pht.base.compiler.java.Compiler
import ru.DmN.pht.base.compiler.java.ctx.CompilationContext
import ru.DmN.pht.base.parser.ast.Node
import ru.DmN.pht.base.parser.ast.NodeNodesList
import ru.DmN.pht.base.utils.Variable
import ru.DmN.pht.base.utils.VirtualType

open class SimpleNodeCompiler<T : NodeNodesList> : NodeCompiler<T>() {
    override fun calc(node: T, compiler: Compiler, ctx: CompilationContext): VirtualType? =
        compiler.calc(node.nodes.last(), ctx)

    override fun compile(node: T, compiler: Compiler, ctx: CompilationContext, ret: Boolean): Variable? {
        node.nodes.dropLast(1).forEach { compiler.compile(it, ctx, false) }
        return compiler.compile(node.nodes.last(), ctx, ret)
    }

    override fun applyAnnotation(node: T, compiler: Compiler, ctx: CompilationContext, annotation: Node) {
        node.nodes.forEach { compiler.applyAnnotation(it, ctx, annotation) }
    }
}