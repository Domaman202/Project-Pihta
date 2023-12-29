package ru.DmN.pht.std.processors

import ru.DmN.pht.std.ast.NodeSet
import ru.DmN.pht.std.node.NodeTypes
import ru.DmN.pht.std.processor.utils.body
import ru.DmN.pht.std.processor.utils.nodeDef
import ru.DmN.pht.std.utils.computeString
import ru.DmN.siberia.Processor
import ru.DmN.siberia.ast.Node
import ru.DmN.siberia.ast.NodeNodesList
import ru.DmN.siberia.processor.ctx.ProcessingContext
import ru.DmN.siberia.processor.utils.ValType
import ru.DmN.siberia.processors.INodeProcessor

object NRDefSet : INodeProcessor<NodeNodesList> {
    override fun process(node: NodeNodesList, processor: Processor, ctx: ProcessingContext, mode: ValType): Node {
        val name = processor.computeString(node.nodes[0], ctx)
        val value = processor.process(node.nodes[1], ctx, ValType.VALUE)!!
        val info = node.info
        return if (ctx.body[name] == null)
            NRDef.process(nodeDef(info, name, value), processor, ctx, mode)
        else NodeSet(info.withType(NodeTypes.SET_), mutableListOf(value), name)
    }
}