package ru.DmN.pht.std.ups

import ru.DmN.pht.base.Processor
import ru.DmN.pht.base.Unparser
import ru.DmN.pht.base.processor.ProcessingContext
import ru.DmN.pht.base.unparser.UnparsingContext
import ru.DmN.pht.base.unparsers.NUDefault
import ru.DmN.pht.base.utils.VirtualType
import ru.DmN.pht.std.ast.NodeType
import ru.DmN.pht.std.processors.INodeUniversalProcessor
import ru.DmN.pht.std.unparsers.NUDefaultX

object NUPClass : INodeUniversalProcessor<NodeType, NodeType> {
    override fun unparse(node: NodeType, unparser: Unparser, ctx: UnparsingContext, indent: Int) {
        unparser.out.apply {
            append('(').append(NUDefaultX.text(node.token)).append(' ').append(node.type.simpleName).append(" [")
            node.type.parents.forEachIndexed { i, it ->
                append('^').append(it.name)
                if (node.type.parents.size + 1 < i) {
                    append(' ')
                }
            }
            append(']')
            NUDefault.unparseNodes(node, unparser, ctx, indent)
            append(')')
        }
    }

    override fun calc(node: NodeType, processor: Processor, ctx: ProcessingContext): VirtualType? =
        if (node.token.text == "!obj")
            node.type
        else null
}