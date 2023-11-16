package ru.DmN.pht.std.ups

import ru.DmN.pht.base.Processor
import ru.DmN.pht.base.Unparser
import ru.DmN.pht.base.processor.ProcessingContext
import ru.DmN.pht.base.unparser.UnparsingContext
import ru.DmN.pht.base.utils.VirtualField
import ru.DmN.pht.base.utils.VirtualType
import ru.DmN.pht.std.ast.NodeFGet
import ru.DmN.pht.std.processor.utils.global
import ru.DmN.pht.std.processors.INodeUniversalProcessor
import ru.DmN.pht.std.unparsers.NUDefaultX
import ru.DmN.pht.std.utils.computeString

object NUPFGetA : INodeUniversalProcessor<NodeFGet, NodeFGet> {
    override fun unparse(node: NodeFGet, unparser: Unparser, ctx: UnparsingContext, indent: Int) {
        unparser.out.apply {
            append('(').append(NUDefaultX.text(node.token)).append(' ')
            unparser.unparse(node.nodes[0], ctx, indent + 1)
            append(' ').append(node.name).append(')')
        }
    }

    override fun calc(node: NodeFGet, processor: Processor, ctx: ProcessingContext): VirtualType? {
        val filter = when (node.type) {
            NodeFGet.Type.UNKNOWN -> { _: VirtualField -> true }
            NodeFGet.Type.STATIC -> { it: VirtualField -> it.isStatic }
            NodeFGet.Type.INSTANCE -> { it: VirtualField -> !it.isStatic }
        }
        return (if (node.type == NodeFGet.Type.STATIC)
            ctx.global.getType(processor.computeString(node.nodes[0], ctx), processor.tp)
        else processor.calc(node.nodes[0], ctx)!!).fields
            .asSequence()
            .filter { it.name == node.name }
            .filter(filter)
            .first()
            .type
    }
}