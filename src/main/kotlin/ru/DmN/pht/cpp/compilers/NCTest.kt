package ru.DmN.pht.cpp.compilers

import ru.DmN.pht.ast.NodeMetaNodesList
import ru.DmN.pht.cpp.compiler.ctx.tests
import ru.DmN.pht.utils.meta.MetadataKeys.TEST
import ru.DmN.siberia.compiler.Compiler
import ru.DmN.siberia.compiler.ctx.CompilationContext
import ru.DmN.siberia.compilers.NCDefault
import ru.DmN.siberia.utils.Variable

object NCTest : ICppNRCompiler<NodeMetaNodesList> {
    override fun compile(node: NodeMetaNodesList, compiler: Compiler, ctx: CompilationContext) {
        compiler.contexts.tests += node.nodes.stream().filter { it.getMetadata(TEST) == true }.count().toInt()
        NCDefault.compile(node, compiler, ctx)
    }

    override fun compileVal(node: NodeMetaNodesList, compiler: Compiler, ctx: CompilationContext): Variable {
        compiler.contexts.tests += node.nodes.stream().filter { it.getMetadata(TEST) == true }.count().toInt()
        return NCDefault.compileVal(node, compiler, ctx)
    }
}