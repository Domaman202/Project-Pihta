package ru.DmN.pht.std.ups

import ru.DmN.siberia.Unparser
import ru.DmN.siberia.ast.NodeNodesList
import ru.DmN.siberia.unparser.UnparsingContext
import ru.DmN.pht.std.imports.ast.NodeAliasType
import ru.DmN.siberia.utils.INUP

object NUPAliasType : INUP<NodeAliasType, NodeAliasType> {
    override fun unparse(node: NodeAliasType, unparser: Unparser, ctx: UnparsingContext, indent: Int) {
        unparser.out.append('(').append(node.token.text).append(' ').append(node.type).append(' ').append(node.new).append(')')
    }
}