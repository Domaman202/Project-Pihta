package ru.DmN.pht.base.unparser.unparsers

import ru.DmN.pht.base.Unparser
import ru.DmN.pht.base.parser.ast.NodeNodesList
import ru.DmN.pht.base.unparser.UnparsingContext

object NUNodesList : NodeUnparser<NodeNodesList>() {
    override fun unparse(unparser: Unparser, ctx: UnparsingContext, node: NodeNodesList) {
        unparser.out.let {
            it.append('(').append(node.tkOperation.text).append(' ')
            node.nodes.forEach { n -> unparser.unparse(ctx, n) }
            it.append(')')
        }
    }
}