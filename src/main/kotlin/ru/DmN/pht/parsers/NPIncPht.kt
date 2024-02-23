package ru.DmN.pht.parsers

import ru.DmN.pht.ast.NodeIncPht
import ru.DmN.pht.node.NodeTypes
import ru.DmN.siberia.Parser
import ru.DmN.siberia.ast.Node
import ru.DmN.siberia.lexer.Token
import ru.DmN.siberia.node.INodeInfo
import ru.DmN.siberia.parser.ctx.ParsingContext
import ru.DmN.siberia.parsers.INodeParser
import ru.DmN.siberia.parsers.NPProgn

object NPIncPht : INodeParser {
    override fun parse(parser: Parser, ctx: ParsingContext, token: Token): Node =
        NPProgn.parse(parser, ctx) { NodeIncPht(INodeInfo.of(NodeTypes.INC_PHT, ctx, token), it, ctx) }
}