package ru.DmN.phtx.pcl.compiler.json.compilers

import ru.DmN.phtx.pcl.ast.NodeNamedElement
import ru.DmN.phtx.pcl.compiler.json.utils.out
import ru.DmN.siberia.Compiler
import ru.DmN.siberia.compiler.ctx.CompilationContext
import ru.DmN.siberia.compilers.INodeCompiler

object NCNamed : INodeCompiler<NodeNamedElement> {
    override fun compile(node: NodeNamedElement, compiler: Compiler, ctx: CompilationContext) {
        ctx.out.append('"').append(node.name).append("\": ")
        compiler.compile(node.node, ctx)
    }
}