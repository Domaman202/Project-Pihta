package ru.DmN.pht.parsers

import ru.DmN.pht.utils.node.NodeTypes.DEBUG
import ru.DmN.siberia.parser.Parser
import ru.DmN.siberia.ast.BaseNode
import ru.DmN.siberia.ast.Node
import ru.DmN.siberia.lexer.Token
import ru.DmN.siberia.parser.ctx.ParsingContext
import ru.DmN.siberia.parsers.INodeParser
import ru.DmN.siberia.utils.node.INodeInfo

object NPDebug : INodeParser {
    override fun parse(parser: Parser, ctx: ParsingContext, token: Token): Node =
        BaseNode(INodeInfo.of(DEBUG, ctx, token))
}