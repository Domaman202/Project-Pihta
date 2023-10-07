package ru.DmN.pht.std.imports.parsers

import ru.DmN.pht.base.Parser
import ru.DmN.pht.base.lexer.Token
import ru.DmN.pht.base.parser.ParsingContext
import ru.DmN.pht.base.parser.ast.Node
import ru.DmN.pht.base.parser.parsers.INodeParser
import ru.DmN.pht.std.imports.ast.NodeValue

object NPValue : INodeParser {
    override fun parse(parser: Parser, ctx: ParsingContext, operationToken: Token): Node =
        when (operationToken.type) {
            Token.Type.OPERATION, Token.Type.STRING ->
                NodeValue(operationToken, operationToken.text!!)
            else -> throw RuntimeException()
        }
}