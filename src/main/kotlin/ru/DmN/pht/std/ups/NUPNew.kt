package ru.DmN.pht.std.ups

import ru.DmN.pht.base.Processor
import ru.DmN.pht.base.Unparser
import ru.DmN.pht.base.processor.utils.ProcessingContext
import ru.DmN.pht.base.unparser.UnparsingContext
import ru.DmN.pht.base.unparsers.NUDefault
import ru.DmN.pht.base.utils.VirtualType
import ru.DmN.pht.std.ast.NodeNew
import ru.DmN.pht.std.processors.INodeUniversalProcessor
import ru.DmN.pht.std.unparsers.NUDefaultX

object NUPNew : INodeUniversalProcessor<NodeNew, NodeNew> {
    override fun unparse(node: NodeNew, unparser: Unparser, ctx: UnparsingContext, indent: Int) {
        unparser.out.apply {
            append('(').append(NUDefaultX.text(node.token)).append(' ').append(NUPValueA.unparseType(node.ctor.declaringClass!!.name))
            NUDefault.unparseNodes(node, unparser, ctx, indent)
            append(')')
        }
    }

    override fun calc(node: NodeNew, processor: Processor, ctx: ProcessingContext): VirtualType? =
        node.ctor.declaringClass
}