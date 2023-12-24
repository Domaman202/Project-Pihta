package ru.DmN.pht.std.processors

import ru.DmN.pht.std.ast.NodeDefMacro
import ru.DmN.pht.std.compiler.java.utils.MacroDefine
import ru.DmN.pht.std.processor.utils.global
import ru.DmN.pht.std.processor.utils.macros
import ru.DmN.pht.std.utils.computeList
import ru.DmN.pht.std.utils.computeString
import ru.DmN.siberia.Processor
import ru.DmN.siberia.ast.Node
import ru.DmN.siberia.processor.ctx.ProcessingContext
import ru.DmN.siberia.processor.utils.ValType
import ru.DmN.siberia.processors.INodeProcessor

object NRDefMacro : INodeProcessor<NodeDefMacro> {
    override fun process(node: NodeDefMacro, processor: Processor, ctx: ProcessingContext, mode: ValType): Node? {
        val name = processor.computeString(node.nodes[0], ctx)
        val args = processor.computeList(node.nodes[1], ctx).map { processor.computeString(it, ctx) }
        val nodes = node.nodes.drop(2).toMutableList()
        val def = MacroDefine(node.uuid, name, args, nodes, ctx.global)
        ctx.global.macros += def
        processor.contexts.macros.getOrPut(ctx.global.namespace) { ArrayList() } += def
        return null
    }
}