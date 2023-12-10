package ru.DmN.phtx.pcl.compiler.json.compilers

import ru.DmN.phtx.pcl.ast.NodeValue
import ru.DmN.phtx.pcl.compiler.json.utils.out
import ru.DmN.siberia.Compiler
import ru.DmN.siberia.compiler.ctx.CompilationContext
import ru.DmN.siberia.compilers.INodeCompiler

object NCValue : INodeCompiler<NodeValue> {
    override fun compile(node: NodeValue, compiler: Compiler, ctx: CompilationContext) {
        ctx.out.apply {
            append('"').append(node.name).append("\": ")
            when (node.type) {
                NodeValue.Type.BOOL_OR_NUM -> append(node.value)
                NodeValue.Type.STRING -> append('"').append(node.value).append('"')
            }
        }
    }
}