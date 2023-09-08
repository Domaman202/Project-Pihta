package ru.DmN.pht.std.math.unparsers

import ru.DmN.pht.base.Unparser
import ru.DmN.pht.base.unparser.UnparsingContext
import ru.DmN.pht.base.unparser.unparsers.NUNodesList
import ru.DmN.pht.std.math.ast.NodeEquals
import ru.DmN.pht.base.unparser.unparsers.INodeUnparser

object NUEquals : INodeUnparser<NodeEquals> {
    override fun unparse(node: NodeEquals, unparser: Unparser, ctx: UnparsingContext, indent: Int) {
        unparser.out.apply {
            append('(').append(when (node.operation) {
                NodeEquals.Operation.EQ -> "="
                NodeEquals.Operation.NE -> "!="
                NodeEquals.Operation.LT -> "<"
                NodeEquals.Operation.LE -> "<"
                NodeEquals.Operation.GT -> "<"
                NodeEquals.Operation.GE -> "<"
            }).append(' ')
            NUNodesList.unparseNodes(node, unparser, ctx, indent)
            append(')')
        }
    }
}