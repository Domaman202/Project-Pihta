package ru.DmN.pht.parsers

import ru.DmN.pht.utils.node.NodeTypes.SKIP
import ru.DmN.siberia.ast.NodeNodesList
import ru.DmN.siberia.lexer.Token
import ru.DmN.siberia.parser.Parser
import ru.DmN.siberia.parser.ctx.ParsingContext
import ru.DmN.siberia.parsers.INodeParser
import ru.DmN.siberia.parsers.NPProgn
import ru.DmN.siberia.utils.node.INodeInfo

object NPSkip : INodeParser {
    override fun parse(parser: Parser, ctx: ParsingContext, token: Token): NodeNodesList {
        NPProgn.skip(parser, ctx, token)
        return NodeNodesList(INodeInfo.Companion.of(SKIP, ctx, token))
    }
}