package ru.DmN.pht.std.ups

import ru.DmN.pht.base.Unparser
import ru.DmN.pht.base.ast.NodeNodesList
import ru.DmN.pht.base.unparser.UnparsingContext
import ru.DmN.pht.base.unparsers.NUDefault
import ru.DmN.pht.std.ast.NodeNs
import ru.DmN.pht.std.processors.INodeUniversalProcessor

object NUPNs : INodeUniversalProcessor<NodeNs, NodeNodesList> {
    override fun unparse(node: NodeNs, unparser: Unparser, ctx: UnparsingContext, indent: Int) {
        unparser.out.apply {
            append('(').append(node.token.text).append(' ').append(node.namespace)
            NUDefault.unparseNodes(node, unparser, ctx, indent)
            append(')')
        }
    }
}