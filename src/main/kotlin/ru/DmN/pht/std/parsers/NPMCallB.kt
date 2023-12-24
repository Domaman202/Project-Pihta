package ru.DmN.pht.std.parsers

import ru.DmN.pht.std.node.NodeParsedTypes
import ru.DmN.siberia.Parser
import ru.DmN.siberia.ast.Node
import ru.DmN.siberia.ast.NodeNodesList
import ru.DmN.siberia.lexer.Token
import ru.DmN.siberia.node.NodeInfoImpl
import ru.DmN.siberia.parser.ctx.ParsingContext
import ru.DmN.siberia.parsers.INodeParser
import ru.DmN.siberia.parsers.NPProgn

object NPMCallB : INodeParser {
    override fun parse(parser: Parser, ctx: ParsingContext, token: Token): Node {
        val name = parser.parseNode(ctx)!!
        return NPProgn.parse(parser, ctx) {
            val list = mutableListOf(it.first())
            list += name
            list += it.drop(1)
            NodeNodesList(NodeInfoImpl.of(NodeParsedTypes.MCALL, ctx, token), list)
        }
    }
}