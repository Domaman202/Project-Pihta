package ru.DmN.pht.std.math.unparsers

import ru.DmN.pht.base.Unparser
import ru.DmN.pht.base.unparser.UnparsingContext
import ru.DmN.pht.base.unparser.unparsers.NUNodesList
import ru.DmN.pht.std.math.ast.NodeMathNA
import ru.DmN.pht.base.unparser.unparsers.NodeUnparser
import ru.DmN.pht.base.utils.indent

object NUMathNA : NodeUnparser<NodeMathNA>() {
    override fun unparse(node: NodeMathNA, unparser: Unparser, ctx: UnparsingContext, indent: Int) {
        unparser.out.run {
            append('(').append(
                when (node.operation) {
                    NodeMathNA.Operation.ADD -> "+"
                    NodeMathNA.Operation.SUB -> "-"
                    NodeMathNA.Operation.MUL -> "*"
                    NodeMathNA.Operation.DIV -> "/"
                }
            ).append(' ')
            NUNodesList.unparseNodes(node, unparser, ctx, indent)
            append(')')
        }
    }
}