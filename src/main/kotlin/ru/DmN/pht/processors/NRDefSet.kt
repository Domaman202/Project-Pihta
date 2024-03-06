package ru.DmN.pht.processors

import ru.DmN.pht.ast.NodeSet
import ru.DmN.pht.processor.utils.body
import ru.DmN.pht.processor.utils.processValues
import ru.DmN.pht.utils.computeString
import ru.DmN.pht.utils.node.NodeTypes.SET_
import ru.DmN.pht.utils.node.nodeDef
import ru.DmN.siberia.processor.Processor
import ru.DmN.siberia.ast.Node
import ru.DmN.siberia.ast.NodeNodesList
import ru.DmN.siberia.processor.ctx.ProcessingContext
import ru.DmN.siberia.processors.INodeProcessor

object NRDefSet : INodeProcessor<NodeNodesList> {
    override fun process(node: NodeNodesList, processor: Processor, ctx: ProcessingContext, valMode: Boolean): Node {
        val name = processor.computeString(node.nodes[0], ctx)
        val value = node.nodes.asSequence().drop(1).processValues(processor, ctx)
        return if (ctx.body[name] == null)
            NRDef.process(nodeDef(node.info, name, value.first()), processor, ctx, valMode)
        else NodeSet(node.info.withType(SET_), value.toMutableList(), name)
    }
}