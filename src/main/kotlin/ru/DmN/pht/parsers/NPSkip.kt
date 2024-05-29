package ru.DmN.pht.parsers

import ru.DmN.pht.utils.node.NodeTypes.CT_SKIP
import ru.DmN.siberia.ast.NodeNodesList
import ru.DmN.siberia.lexer.Token
import ru.DmN.siberia.lexer.Token.DefaultType.CLOSE_BRACKET
import ru.DmN.siberia.parser.Parser
import ru.DmN.siberia.parser.ctx.ParsingContext
import ru.DmN.siberia.parsers.INodeParser
import ru.DmN.siberia.parsers.NPProgn
import ru.DmN.siberia.utils.node.INodeInfo

object NPSkip : INodeParser {
    override fun parse(parser: Parser, ctx: ParsingContext, token: Token): NodeNodesList {
        NPProgn.skip(parser, ctx, token)
        parser.pushToken(Token(token.line, token.ptr, CLOSE_BRACKET))
        return NodeNodesList(INodeInfo.Companion.of(CT_SKIP, ctx, token))
    }
}