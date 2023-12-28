package ru.DmN.pht.std.parsers

import ru.DmN.pht.std.ast.NodeFieldA
import ru.DmN.pht.std.node.NodeParsedTypes
import ru.DmN.siberia.Parser
import ru.DmN.siberia.ast.Node
import ru.DmN.siberia.lexer.Token
import ru.DmN.siberia.node.INodeInfo
import ru.DmN.siberia.parser.ctx.ParsingContext
import ru.DmN.siberia.parsers.INodeParser
import ru.DmN.siberia.parsers.NPProgn

object NPFld : INodeParser {
    override fun parse(parser: Parser, ctx: ParsingContext, token: Token): Node =
        NPProgn.parse(parser, ctx) { NodeFieldA(INodeInfo.of(NodeParsedTypes.FLD, ctx, token), it) }
}