package ru.DmN.pht.std.base.parsers

import ru.DmN.pht.base.Parser
import ru.DmN.pht.base.lexer.Token
import ru.DmN.pht.base.parser.ParsingContext
import ru.DmN.pht.base.parser.parsers.SimpleNP
import ru.DmN.pht.std.base.ast.NodeNamespace
import ru.DmN.pht.base.utils.nextOperation

object NPNs : SimpleNP<NodeNamespace>() {
    override fun parse(parser: Parser, ctx: ParsingContext, operationToken: Token): NodeNamespace {
        val name = parser.nextOperation().text!!
        return parse(parser, ctx) { NodeNamespace(operationToken, name, it) }
    }
}