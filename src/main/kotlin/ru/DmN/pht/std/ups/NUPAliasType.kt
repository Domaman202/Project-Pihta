package ru.DmN.pht.std.ups

import ru.DmN.pht.base.Unparser
import ru.DmN.pht.base.ast.NodeNodesList
import ru.DmN.pht.base.unparser.UnparsingContext
import ru.DmN.pht.std.imports.ast.NodeAliasType
import ru.DmN.pht.std.processors.INodeUniversalProcessor

object NUPAliasType : INodeUniversalProcessor<NodeAliasType, NodeNodesList> {
    override fun unparse(node: NodeAliasType, unparser: Unparser, ctx: UnparsingContext, indent: Int) {
        unparser.out.append('(').append(node.token.text).append(' ').append(node.type).append(' ').append(node.new).append(')')
    }
}