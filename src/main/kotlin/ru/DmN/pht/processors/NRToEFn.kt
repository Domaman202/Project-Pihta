package ru.DmN.pht.processors

import ru.DmN.pht.processor.ctx.global
import ru.DmN.pht.processor.utils.computeList
import ru.DmN.pht.processor.utils.computeString
import ru.DmN.pht.processor.utils.computeType
import ru.DmN.pht.utils.vtype.FakeExtVirtualMethod
import ru.DmN.siberia.ast.Node
import ru.DmN.siberia.ast.NodeNodesList
import ru.DmN.siberia.processor.Processor
import ru.DmN.siberia.processor.ctx.ProcessingContext
import ru.DmN.siberia.processors.INodeProcessor

object NRToEFn : INodeProcessor<NodeNodesList> {
    override fun process(node: NodeNodesList, processor: Processor, ctx: ProcessingContext, valMode: Boolean): Node? {
        val decl = processor.computeType(node.nodes[0], ctx)
        val name = processor.computeString(node.nodes[1], ctx)
        val args = processor.computeList(node.nodes[2], ctx).map { processor.computeType(it, ctx) }
        val ext = args[0]
        val fake = FakeExtVirtualMethod(decl.methods.find { it.name == name && it.argsc == args } ?: NRMCall.throwMNF(decl, name, args),ext )
        (decl.methods as MutableList) += fake
        ctx.global.getExtensions(ext) += fake
        return null
    }
}