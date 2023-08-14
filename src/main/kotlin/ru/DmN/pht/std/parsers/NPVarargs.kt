package ru.DmN.pht.std.parsers

import ru.DmN.pht.base.Parser
import ru.DmN.pht.base.lexer.Token
import ru.DmN.pht.std.ast.IStaticVariantNode
import ru.DmN.pht.base.parser.ast.NodeNodesList
import ru.DmN.pht.base.parser.parsers.SimpleNodeParser
import ru.DmN.pht.std.ast.IVarargsVariantNode

object NPVarargs : SimpleNodeParser<NodeNodesList>() {
    override fun parse(parser: Parser, operationToken: Token): NodeNodesList =
        super.parse(parser, operationToken).apply {
            nodes.forEach {
                if (it is IVarargsVariantNode) {
                    it.varargs = true
                }
            }
        }
}