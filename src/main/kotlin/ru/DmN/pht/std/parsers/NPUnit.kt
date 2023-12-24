package ru.DmN.pht.std.parsers

import ru.DmN.pht.std.node.NodeTypes
import ru.DmN.siberia.Parser
import ru.DmN.siberia.ast.Node
import ru.DmN.siberia.lexer.Token
import ru.DmN.siberia.node.NodeInfoImpl
import ru.DmN.siberia.parser.ctx.ParsingContext
import ru.DmN.siberia.parsers.INodeParser

object NPUnit : INodeParser {
    override fun parse(parser: Parser, ctx: ParsingContext, token: Token): Node =
        Node(NodeInfoImpl.of(NodeTypes.UNIT, ctx, token))
}