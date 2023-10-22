package ru.DmN.pht.std.module.parsers

import ru.DmN.pht.base.Parser
import ru.DmN.pht.base.lexer.Token
import ru.DmN.pht.base.parser.ParsingContext
import ru.DmN.pht.base.ast.Node
import ru.DmN.pht.base.parsers.INodeParser
import ru.DmN.pht.std.module.ast.NodeValue

object NPValue : INodeParser {
    override fun parse(parser: Parser, ctx: ParsingContext, operationToken: Token): Node? =
        when (operationToken.type) {
            Token.Type.STRING, Token.Type.INTEGER ->
                NodeValue(operationToken, operationToken.text!!)
            else -> throw RuntimeException()
        }
}