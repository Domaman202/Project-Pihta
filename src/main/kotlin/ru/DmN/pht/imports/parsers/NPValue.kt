package ru.DmN.pht.imports.parsers

import ru.DmN.pht.imports.ast.NodeValue
import ru.DmN.pht.utils.node.NodeTypes
import ru.DmN.siberia.ast.Node
import ru.DmN.siberia.lexer.Token
import ru.DmN.siberia.lexer.Token.DefaultType.OPERATION
import ru.DmN.siberia.lexer.Token.DefaultType.STRING
import ru.DmN.siberia.parser.Parser
import ru.DmN.siberia.parser.ctx.ParsingContext
import ru.DmN.siberia.parsers.INodeParser
import ru.DmN.siberia.utils.node.INodeInfo

object NPValue : INodeParser {
    override fun parse(parser: Parser, ctx: ParsingContext, token: Token): Node =
        when (token.type) {
            OPERATION, STRING -> NodeValue(INodeInfo.of(NodeTypes.VALUE, ctx, token), token.text!!)
            else -> throw RuntimeException()
        }
}