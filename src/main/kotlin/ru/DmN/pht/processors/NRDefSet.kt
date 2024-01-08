package ru.DmN.pht.std.processors

import ru.DmN.pht.std.ast.NodeSet
import ru.DmN.pht.std.node.NodeTypes
import ru.DmN.pht.std.node.nodeDef
import ru.DmN.pht.std.processor.utils.body
import ru.DmN.pht.std.processor.utils.processValues
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
        val value = node.nodes.asSequence().drop(1).processValues(processor, ctx)
        return if (ctx.body[name] == null)
            NRDef.process(nodeDef(node.info, name, value.first()), processor, ctx, mode)
        else NodeSet(node.info.withType(NodeTypes.SET_), value.toMutableList(), name)
    }
}