package ru.DmN.pht.std.oop.parsers

import ru.DmN.pht.base.Parser
import ru.DmN.pht.base.lexer.Token
import ru.DmN.pht.base.parser.ParsingContext
import ru.DmN.pht.base.parser.ast.Node
import ru.DmN.pht.base.parser.ast.NodeNodesList
import ru.DmN.pht.base.parser.parsers.INodeParser
import ru.DmN.pht.base.parser.parsers.NPDefault

object NPMCallB : INodeParser {
    override fun parse(parser: Parser, ctx: ParsingContext, operationToken: Token): Node {
        val name = parser.parseNode(ctx)!!
        return NPDefault.parse(parser, ctx) {
            val list = mutableListOf(it.first())
            list += name
            list += it.drop(1)
            NodeNodesList(Token(operationToken.line, operationToken.type, "mcall"), list)
        }
    }
}