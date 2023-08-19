package ru.DmN.pht.std.compiler.java.compilers

import ru.DmN.pht.base.compiler.java.Compiler
import ru.DmN.pht.base.compiler.java.ctx.CompilationContext
import ru.DmN.pht.base.compiler.java.utils.CompileStage
import ru.DmN.pht.base.compiler.java.utils.MacroDefine
import ru.DmN.pht.base.parser.ast.Node
import ru.DmN.pht.base.utils.Variable
import ru.DmN.pht.std.ast.NodeMacroDef
import ru.DmN.pht.std.compiler.java.compute
import ru.DmN.pht.std.compiler.java.computeName
import ru.DmN.pht.std.compiler.java.global

object NCDefMacro : IStdNodeCompiler<NodeMacroDef> {
    override fun compile(node: NodeMacroDef, compiler: Compiler, ctx: CompilationContext, ret: Boolean): Variable? {
        compiler.tasks[CompileStage.MACROS_DEFINE_IMPORT].add {
            val gctx = ctx.global
            val nodes = node.nodes.map { { name: Boolean -> compiler.compute<Any?>(it, ctx, name) } }
            val macro = MacroDefine(
                node.name,
                (nodes[0](false) as List<Node>).map { "${compiler.computeName(it, ctx)}$${node.name}" },
                nodes.drop(1).map { it(true) as Node },
                gctx
            )
            gctx.macros += macro
            compiler.macros.getOrPut(gctx.namespace) { ArrayList() } += macro
        }
        return null
    }
}