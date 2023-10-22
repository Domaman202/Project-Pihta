package ru.DmN.pht.std.ups

import ru.DmN.pht.base.Parser
import ru.DmN.pht.base.ast.Node
import ru.DmN.pht.base.ast.NodeNodesList
import ru.DmN.pht.base.lexer.Token
import ru.DmN.pht.base.parser.ParsingContext
import ru.DmN.pht.base.parsers.NPDefault
import ru.DmN.pht.std.processors.INodeUniversalProcessor

object NUPMCallB : INodeUniversalProcessor<Node, Node> {
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