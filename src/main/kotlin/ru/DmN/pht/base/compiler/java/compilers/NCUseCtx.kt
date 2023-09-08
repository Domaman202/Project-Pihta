package ru.DmN.pht.base.compiler.java.compilers

import ru.DmN.pht.base.Parser
import ru.DmN.pht.base.compiler.java.Compiler
import ru.DmN.pht.base.compiler.java.ctx.CompilationContext
import ru.DmN.pht.base.compiler.java.utils.CompileStage
import ru.DmN.pht.base.parser.ParsingContext
import ru.DmN.pht.base.parser.ast.NodeUseCtx
import ru.DmN.pht.base.utils.Module
import ru.DmN.pht.base.utils.Variable
import ru.DmN.pht.std.module.StdModule

object NCUseCtx : INodeCompiler<NodeUseCtx> {
    override fun compile(node: NodeUseCtx, compiler: Compiler, ctx: CompilationContext, ret: Boolean): Variable? {
        node.names.forEach { process(it, compiler) }
        compiler.pushTask(ctx, CompileStage.MODULE_POST_INIT) {
            val context = ctx.subCtx()
            node.names.map { name -> Module.MODULES[name]!!.inject(compiler, context, ret) }
            NCDefault.compile(node, compiler, context, ret)
        }
        return null
    }

    fun process(name: String, compiler: Compiler) {
        compiler.compile(
            Parser(Module.getModuleFile(name)).parseNode(ParsingContext.of(StdModule))!!,
            CompilationContext.base().apply { loadedModules += StdModule },
            false
        )
    }
}