package ru.DmN.pht.std.imports.parsers

import ru.DmN.pht.base.Parser
import ru.DmN.pht.base.lexer.Token
import ru.DmN.pht.base.parser.ctx.ParsingContext
import ru.DmN.pht.base.ast.Node
import ru.DmN.pht.base.parsers.INodeParser
import ru.DmN.pht.std.imports.ast.NodeValue

object NPValue : INodeParser {
    override fun parse(parser: Parser, ctx: ParsingContext, token: Token): Node =
        when (token.type) {
            Token.Type.OPERATION, Token.Type.STRING -> NodeValue(token, token.text!!)
            else -> throw RuntimeException()
        }
}