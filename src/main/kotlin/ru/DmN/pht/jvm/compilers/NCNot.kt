package ru.DmN.pht.jvm.compilers

import ru.DmN.pht.ast.NodeCompare
import ru.DmN.pht.ast.NodeMath
import ru.DmN.siberia.ast.NodeNodesList
import ru.DmN.siberia.compiler.Compiler
import ru.DmN.siberia.compiler.ctx.CompilationContext
import ru.DmN.siberia.utils.Variable

object NCNot : IValueNodeCompiler<NodeNodesList> {
    override fun compileVal(node: NodeNodesList, compiler: Compiler, ctx: CompilationContext): Variable =
        if (node is NodeCompare)
            NCCompare.compileVal(node, compiler, ctx)
        else NCMath.compileVal(node as NodeMath,  compiler, ctx)
}