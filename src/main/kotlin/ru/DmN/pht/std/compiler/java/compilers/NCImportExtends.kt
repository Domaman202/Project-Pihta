package ru.DmN.pht.std.compiler.java.compilers

import ru.DmN.pht.base.compiler.java.CompileStage
import ru.DmN.pht.base.compiler.java.Compiler
import ru.DmN.pht.base.compiler.java.compilers.NodeCompiler
import ru.DmN.pht.base.compiler.java.ctx.CompilationContext
import ru.DmN.pht.base.parser.ast.Node
import ru.DmN.pht.base.utils.Variable
import ru.DmN.pht.base.parser.ast.NodeNodesList

object NCImportExtends : NodeCompiler<NodeNodesList>() {
    override fun compile(node: NodeNodesList, compiler: Compiler, ctx: CompilationContext, ret: Boolean): Variable? {
        if (ctx.type == CompilationContext.Type.GLOBAL) {
            compiler.tasks[CompileStage.EXTENDS_IMPORT].add {
                compiler.compute<List<Node>>(node.nodes.first(), ctx, false).forEach { it ->
                    ctx.global.getType(compiler, compiler.computeStringConst(it, ctx)).methods.filter { it.extend != null }.forEach {
                        ctx.global.getExtends(it.extend!!) += it
                    }
                }
            }
        }
        return null
    }
}