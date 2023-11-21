package ru.DmN.pht.std.ups

import ru.DmN.pht.base.Unparser
import ru.DmN.pht.base.unparser.UnparsingContext
import ru.DmN.pht.std.ast.NodeASet
import ru.DmN.pht.std.processors.INodeUniversalProcessor

object NUPASet : INodeUniversalProcessor<NodeASet, NodeASet> {
    override fun unparse(node: NodeASet, unparser: Unparser, ctx: UnparsingContext, indent: Int) {
        unparser.out.apply {
            append('(').append("aset").append('\n').append("\t".repeat(indent + 1))
            unparser.unparse(node.arr, ctx, indent + 1)
            append('\n').append("\t".repeat(indent + 1))
            unparser.unparse(node.index, ctx, indent + 1)
            append('\n').append("\t".repeat(indent + 1))
            unparser.unparse(node.value, ctx, indent + 1)
            append(')')
        }
    }
}