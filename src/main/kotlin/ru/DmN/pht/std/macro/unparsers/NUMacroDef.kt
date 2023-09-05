package ru.DmN.pht.std.macro.unparsers

import ru.DmN.pht.base.Unparser
import ru.DmN.pht.base.unparser.UnparsingContext
import ru.DmN.pht.base.unparser.unparsers.NUNodesList
import ru.DmN.pht.base.unparser.unparsers.NodeUnparser
import ru.DmN.pht.base.utils.indent
import ru.DmN.pht.std.macro.ast.NodeMacroDef

object NUMacroDef : NodeUnparser<NodeMacroDef>() {
    override fun unparse(node: NodeMacroDef, unparser: Unparser, ctx: UnparsingContext, indent: Int) {
        unparser.out.apply {
            append('(').append(node.tkOperation.text).append(' ').append(node.name)
            NUNodesList.unparseNodes(node, unparser, ctx, indent)
            append(')')
        }
    }
}