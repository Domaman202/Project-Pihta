package ru.DmN.pht.std.ups

import ru.DmN.pht.base.Unparser
import ru.DmN.pht.base.unparser.UnparsingContext
import ru.DmN.pht.base.unparsers.NUDefault
import ru.DmN.pht.std.ast.NodeMCall
import ru.DmN.pht.std.processors.INodeUniversalProcessor
import ru.DmN.pht.std.unparsers.NUDefaultX

object NUPMCallX : INodeUniversalProcessor<NodeMCall, NodeMCall> {
    override fun unparse(node: NodeMCall, unparser: Unparser, ctx: UnparsingContext, indent: Int) {
        unparser.out.apply {
            append('(').append(NUDefaultX.text(node.token)).append(' ')
            unparser.unparse(node.instance, ctx, indent + 1)
            append(' ').append(node.method.name)
            NUDefault.unparseNodes(node, unparser, ctx, indent)
            append(')')
        }
    }
}