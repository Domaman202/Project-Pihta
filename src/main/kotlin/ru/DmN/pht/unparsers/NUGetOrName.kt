package ru.DmN.pht.unparsers

import ru.DmN.pht.ast.NodeGetOrName
import ru.DmN.siberia.Unparser
import ru.DmN.siberia.unparser.UnparsingContext
import ru.DmN.siberia.unparsers.INodeUnparser

object NUGetOrName : INodeUnparser<NodeGetOrName> {
    override fun unparse(node: NodeGetOrName, unparser: Unparser, ctx: UnparsingContext, indent: Int) {
        unparser.out.append(node.name)
    }
}