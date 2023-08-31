package ru.DmN.pht.std.macro.compiler.java.compilers

import ru.DmN.pht.base.Compiler
import ru.DmN.pht.base.compiler.java.ctx.CompilationContext
import ru.DmN.pht.base.parser.ast.Node
import ru.DmN.pht.base.utils.Variable
import ru.DmN.pht.base.utils.VirtualType
import ru.DmN.pht.std.base.compiler.java.compilers.IStdNodeCompiler
import ru.DmN.pht.std.macro.ast.NodeMacroUnroll
import ru.DmN.pht.std.base.compiler.java.ctx.MacroContext
import ru.DmN.pht.std.base.compiler.java.utils.*

object NCMacroUnroll : IStdNodeCompiler<NodeMacroUnroll> {
    override fun calc(node: NodeMacroUnroll, compiler: Compiler, ctx: CompilationContext): VirtualType? {
        val mctx = ctx.macro
        val expr = compiler.compute<Node>(node.nodes.last(), ctx, ComputeType.NODE)
        val context = MacroContext(mctx.args)
        compiler.computeNL(node.nodes.first(), ctx)
            .map { it -> compiler.computeNL(it, ctx).map { compiler.computeName(it, ctx) } }
            .forEach { it ->
                context["${it.first()}$${node.macro}"] =
                    compiler.compute<Any?>(mctx["${it.last()}$${node.macro}"], ctx, ComputeType.NODE)
                        .let { if (it is Node) listOf(it) else it as List<Node> }
                        .last()
            }
        return compiler.calc(expr, ctx.with(context))
    }

    override fun compile(node: NodeMacroUnroll, compiler: Compiler, ctx: CompilationContext, ret: Boolean): Variable? {
        val mctx = ctx.macro
        val expr = compiler.compute<Node>(node.nodes.last(), ctx, ComputeType.NODE)
        val contexts = ArrayList<MacroContext>()
        compiler.computeNL(node.nodes.first(), ctx)
            .map { it -> compiler.computeNL(it, ctx).map { compiler.computeName(it, ctx) } }
            .forEach { it ->
                val name = "${it.first()}$${node.macro}"
                compiler.compute<Any?>(mctx["${it.last()}$${node.macro}"], ctx, ComputeType.NODE)
                    .let { if (it is Node) listOf(it) else it as List<Node> }
                    .forEachIndexed { i, v ->
                        contexts.getOrElse(i) { MacroContext().apply { contexts += this } }[name] = v
                    }
            }
        contexts.dropLast(1).forEach { compiler.compile(expr, ctx.with(it), false) }
        return compiler.compile(expr, ctx.with(contexts.last()), ret)
    }
}