package ru.DmN.pht.std.base.compiler.java.compilers

import ru.DmN.pht.base.compiler.java.Compiler
import ru.DmN.pht.base.compiler.java.ctx.CompilationContext
import ru.DmN.pht.base.compiler.java.utils.CompileStage
import ru.DmN.pht.base.parser.ast.Node
import ru.DmN.pht.base.parser.ast.NodeNodesList
import ru.DmN.pht.base.utils.Variable
import ru.DmN.pht.std.base.compiler.java.utils.*

object NCImportMacro : IStdNodeCompiler<NodeNodesList> {
    override fun compile(node: NodeNodesList, compiler: Compiler, ctx: CompilationContext, ret: Boolean): Variable? {
        if (ctx.isGlobal()) {
            compiler.pushTask(ctx, CompileStage.MACROS_IMPORT) {
                val gctx = ctx.global
                node.nodes.map { it -> compiler.compute<List<Node>>(it, ctx, ComputeType.NAME).map { compiler.computeName(it, ctx) } }.first().forEach { it ->
                    val macro = it.substring(it.lastIndexOf('.') + 1)
                    val macros = compiler.contexts.macros[it.substring(0, it.lastIndexOf('.'))]!!
                    if (macro == "*")
                        gctx.macros += macros
                    else gctx.macros += macros.find { it.name == macro }!!
                }
            }
        }
        return null
    }
}