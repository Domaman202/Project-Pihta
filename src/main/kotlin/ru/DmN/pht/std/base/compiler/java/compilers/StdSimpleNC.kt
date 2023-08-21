package ru.DmN.pht.std.base.compiler.java.compilers

import ru.DmN.pht.base.compiler.java.Compiler
import ru.DmN.pht.base.compiler.java.compilers.SimpleNC
import ru.DmN.pht.base.compiler.java.ctx.CompilationContext
import ru.DmN.pht.base.parser.ast.Node
import ru.DmN.pht.base.parser.ast.NodeNodesList
import ru.DmN.pht.std.base.compiler.java.utils.applyAnnotation

open class StdSimpleNC<T : NodeNodesList> : SimpleNC<T>(), IStdNodeCompiler<T> {
    override fun applyAnnotation(node: T, compiler: Compiler, ctx: CompilationContext, annotation: Node) =
        node.nodes.forEach { compiler.applyAnnotation(it, ctx, annotation) }
}