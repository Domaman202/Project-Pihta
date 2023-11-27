package ru.DmN.pht.std.imports.parsers

import ru.DmN.siberia.Parser
import ru.DmN.siberia.lexer.Token
import ru.DmN.siberia.parser.ctx.ParsingContext
import ru.DmN.siberia.ast.Node
import ru.DmN.siberia.parsers.INodeParser
import ru.DmN.pht.std.imports.ast.NodeValue

object NPValue : INodeParser {
    override fun parse(parser: Parser, ctx: ParsingContext, token: Token): Node =
        when (token.type) {
            Token.Type.OPERATION, Token.Type.STRING -> NodeValue(token, token.text!!)
            else -> throw RuntimeException()
        }
}