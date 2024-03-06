package ru.DmN.pht.parsers

import ru.DmN.pht.utils.node.NodeParsedTypes.MCALL
import ru.DmN.siberia.parser.Parser
import ru.DmN.siberia.ast.Node
import ru.DmN.siberia.ast.NodeNodesList
import ru.DmN.siberia.lexer.Token
import ru.DmN.siberia.parser.ctx.ParsingContext
import ru.DmN.siberia.parsers.INodeParser
import ru.DmN.siberia.parsers.NPProgn
import ru.DmN.siberia.utils.node.INodeInfo

object NPMCallB : INodeParser {
    override fun parse(parser: Parser, ctx: ParsingContext, token: Token): Node {
        val name = parser.parseNode(ctx)!!
        return NPProgn.parse(parser, ctx) {
            val list = mutableListOf(it.first())
            list += name
            list += it.drop(1)
            NodeNodesList(INodeInfo.of(MCALL, ctx, token), list)
        }
    }
}