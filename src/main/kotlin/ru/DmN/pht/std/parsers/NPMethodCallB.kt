package ru.DmN.pht.std.parsers

import ru.DmN.pht.base.Parser
import ru.DmN.pht.base.lexer.Token
import ru.DmN.pht.base.parser.ParsingContext
import ru.DmN.pht.base.parser.ast.NodeNodesList
import ru.DmN.pht.base.parser.parsers.SimpleNP

object NPMethodCallB : SimpleNP<NodeNodesList>() {
    override fun parse(parser: Parser, ctx: ParsingContext, operationToken: Token): NodeNodesList {
        val name = parser.parseNode(ctx)!!
        return super.parse(parser, ctx) {
            val list = mutableListOf(it.first())
            list += name
            list += it.drop(1)
            NodeNodesList(Token(operationToken.line, operationToken.type, "mcall"), list)
        }
    }
}