package ru.DmN.pht.std.base.compiler.java.compilers

import ru.DmN.pht.base.Compiler
import ru.DmN.pht.base.compiler.java.ctx.CompilationContext
import ru.DmN.pht.base.compiler.java.utils.CompileStage
import ru.DmN.pht.base.parser.ast.Node
import ru.DmN.pht.base.parser.ast.NodeNodesList
import ru.DmN.pht.base.utils.Variable
import ru.DmN.pht.std.base.compiler.java.utils.*

object NCImportExtends : IStdNodeCompiler<NodeNodesList> {
    override fun compile(node: NodeNodesList, compiler: Compiler, ctx: CompilationContext, ret: Boolean): Variable? {
        if (ctx.isGlobal()) {
            compiler.pushTask(ctx, CompileStage.EXTENDS_IMPORT) {
                val gctx = ctx.global
                compiler.compute<List<Node>>(node.nodes.first(), ctx, ComputeType.NODE).forEach { it ->
                    gctx.getType(compiler, compiler.computeName(it, ctx)).methods
                        .filter { it.extend != null }
                        .forEach { gctx.getExtends(it.extend!!) += it }
                }
            }
        }
        return null
    }
}