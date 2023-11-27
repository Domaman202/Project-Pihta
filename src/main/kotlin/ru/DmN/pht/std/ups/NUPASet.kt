package ru.DmN.pht.std.ups

import ru.DmN.siberia.Unparser
import ru.DmN.siberia.unparser.UnparsingContext
import ru.DmN.pht.std.ast.NodeASet
import ru.DmN.siberia.utils.INUP

object NUPASet : INUP<NodeASet, NodeASet> {
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