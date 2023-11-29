package ru.DmN.pht.std.ups

import ru.DmN.siberia.Processor
import ru.DmN.siberia.Unparser
import ru.DmN.siberia.ast.NodeNodesList
import ru.DmN.siberia.processor.utils.ProcessingContext
import ru.DmN.siberia.unparser.UnparsingContext
import ru.DmN.siberia.utils.INUP
import ru.DmN.siberia.utils.VirtualType

object NUPRet : INUP<NodeNodesList, NodeNodesList> {
    override fun unparse(node: NodeNodesList, unparser: Unparser, ctx: UnparsingContext, indent: Int) =
        NUPDefaultX.unparse(node, unparser, ctx, indent)

    override fun calc(node: NodeNodesList, processor: Processor, ctx: ProcessingContext): VirtualType? =
        if (node.nodes.isEmpty()) null else processor.calc(node.nodes[0], ctx)
}