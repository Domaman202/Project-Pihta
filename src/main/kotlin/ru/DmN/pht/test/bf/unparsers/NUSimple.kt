package ru.DmN.pht.test.bf.unparsers

import ru.DmN.pht.base.Unparser
import ru.DmN.pht.base.parser.ast.Node
import ru.DmN.pht.base.unparser.UnparsingContext
import ru.DmN.pht.base.unparser.unparsers.NodeUnparser

object NUSimple : NodeUnparser<Node>() {
    override fun unparse(unparser: Unparser, ctx: UnparsingContext, node: Node) {
        unparser.out.append('(').append(node.tkOperation.text).append(')')
    }
}