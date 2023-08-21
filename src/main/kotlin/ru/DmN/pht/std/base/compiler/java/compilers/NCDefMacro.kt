package ru.DmN.pht.std.base.compiler.java.compilers

import ru.DmN.pht.base.compiler.java.Compiler
import ru.DmN.pht.base.compiler.java.ctx.CompilationContext
import ru.DmN.pht.base.compiler.java.utils.CompileStage
import ru.DmN.pht.base.parser.ast.Node
import ru.DmN.pht.base.utils.Variable
import ru.DmN.pht.std.base.ast.NodeMacroDef
import ru.DmN.pht.std.base.compiler.java.utils.*

object NCDefMacro : IStdNodeCompiler<NodeMacroDef> {
    override fun compile(node: NodeMacroDef, compiler: Compiler, ctx: CompilationContext, ret: Boolean): Variable? {
        compiler.tasks[CompileStage.MACROS_DEFINE].add {
            val gctx = ctx.global
            val nodes = node.nodes.map { { type: ComputeType -> compiler.compute<Any?>(it, ctx, type) } }
            val macro = MacroDefine(
                node.name,
                (nodes[0](ComputeType.NODE) as List<Node>).map { "${compiler.computeName(it, ctx)}$${node.name}" },
                nodes.drop(1).map { it(ComputeType.NODE) as Node },
                gctx
            )
            gctx.macros += macro
            compiler.contexts.macros.getOrPut(gctx.namespace) { ArrayList() } += macro
        }
        return null
    }
}