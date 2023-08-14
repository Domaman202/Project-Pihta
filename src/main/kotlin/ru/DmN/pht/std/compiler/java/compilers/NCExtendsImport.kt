package ru.DmN.pht.std.compiler.java.compilers

import ru.DmN.pht.base.compiler.java.CompileStage
import ru.DmN.pht.base.compiler.java.Compiler
import ru.DmN.pht.base.compiler.java.compilers.NodeCompiler
import ru.DmN.pht.base.compiler.java.ctx.CompilationContext
import ru.DmN.pht.base.utils.Variable
import ru.DmN.pht.base.parser.ast.NodeNodesList

object NCExtendsImport : NodeCompiler<NodeNodesList>() {
    override fun compile(node: NodeNodesList, compiler: Compiler, ctx: CompilationContext, ret: Boolean): Variable? {
        if (ctx.type == CompilationContext.Type.GLOBAL) {
            compiler.tasks[CompileStage.EXTENDS_IMPORT].add {
                node.nodes.forEach { it ->
                    ctx.gctx.getType(compiler, it.getConstValueAsString()).methods.filter { it.extend != null }
                        .forEach {
                            ctx.gctx.getExtends(it.extend!!) += it
                        }
                }
            }
        }
        return null
    }
}