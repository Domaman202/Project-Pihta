package ru.DmN.pht.std.ups

import ru.DmN.pht.base.Processor
import ru.DmN.pht.base.Unparser
import ru.DmN.pht.base.processor.utils.ProcessingContext
import ru.DmN.pht.base.unparser.UnparsingContext
import ru.DmN.pht.base.utils.VirtualType
import ru.DmN.pht.std.ast.NodeAs
import ru.DmN.pht.std.processors.INodeUniversalProcessor
import ru.DmN.pht.std.unparsers.NUDefaultX

object NUPAs : INodeUniversalProcessor<NodeAs, NodeAs> {
    override fun unparse(node: NodeAs, unparser: Unparser, ctx: UnparsingContext, indent: Int) {
        unparser.out.run {
            append('(').append(NUDefaultX.text(node.token)).append(' ').append(NUPValueA.unparseType(node.type.name)).append('\n').append("\t".repeat(indent + 1))
            unparser.unparse(node.nodes[0], ctx, indent + 1)
            append(')')
        }
    }

    override fun calc(node: NodeAs, processor: Processor, ctx: ProcessingContext): VirtualType =
        node.type
}