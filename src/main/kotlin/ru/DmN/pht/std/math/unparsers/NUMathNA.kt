package ru.DmN.pht.std.math.unparsers

import ru.DmN.pht.base.Unparser
import ru.DmN.pht.base.unparser.UnparsingContext
import ru.DmN.pht.std.math.ast.NodeMathNA
import ru.DmN.pht.base.unparser.unparsers.NodeUnparser

object NUMathNA : NodeUnparser<NodeMathNA>() {
    override fun unparse(unparser: Unparser, ctx: UnparsingContext, node: NodeMathNA) {
        unparser.out.append('(').append(when (node.operation) {
            NodeMathNA.Operation.ADD -> "+"
            NodeMathNA.Operation.SUB -> "-"
            NodeMathNA.Operation.MUL -> "*"
            NodeMathNA.Operation.DIV -> "/"
        }).append(' ')
        node.nodes.forEach { unparser.unparse(ctx, it) }
        unparser.out.append(')')
    }
}