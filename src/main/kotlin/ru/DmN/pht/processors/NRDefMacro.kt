package ru.DmN.pht.processors

import ru.DmN.pht.ast.NodeDefMacro
import ru.DmN.pht.compiler.java.utils.MacroDefine
import ru.DmN.pht.processor.ctx.global
import ru.DmN.pht.processor.ctx.macros
import ru.DmN.pht.utils.computeList
import ru.DmN.pht.utils.computeString
import ru.DmN.siberia.ast.Node
import ru.DmN.siberia.processor.Processor
import ru.DmN.siberia.processor.ctx.ProcessingContext
import ru.DmN.siberia.processors.INodeProcessor

object NRDefMacro : INodeProcessor<NodeDefMacro> {
    override fun process(node: NodeDefMacro, processor: Processor, ctx: ProcessingContext, valMode: Boolean): Node? {
        val name = processor.computeString(node.nodes[0], ctx)
        val args = processor.computeList(node.nodes[1], ctx).map { processor.computeString(it, ctx) }
        val nodes = node.nodes.drop(2).toMutableList()
        val def = MacroDefine(node.uuid, name, args, nodes, ctx.global)
        ctx.global.macros += def
        processor.contexts.macros.getOrPut(ctx.global.namespace) { ArrayList() } += def
        return null
    }
}