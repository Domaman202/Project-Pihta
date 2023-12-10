package ru.DmN.phtx.pcl.compiler.json.compilers

import ru.DmN.phtx.pcl.ast.NodeValue
import ru.DmN.phtx.pcl.compiler.json.utils.out
import ru.DmN.siberia.Compiler
import ru.DmN.siberia.compiler.ctx.CompilationContext
import ru.DmN.siberia.compilers.INodeCompiler

object NCValue : INodeCompiler<NodeValue> {
    override fun compile(node: NodeValue, compiler: Compiler, ctx: CompilationContext) {
        when (node.type) {
            NodeValue.Type.BON -> ctx.out.append(node.value)
            NodeValue.Type.STRING -> ctx.out.append('"').append(node.value).append('"')
        }
    }
}