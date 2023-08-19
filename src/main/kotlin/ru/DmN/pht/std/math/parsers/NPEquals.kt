package ru.DmN.pht.std.math.parsers

import ru.DmN.pht.base.lexer.Token
import ru.DmN.pht.base.Parser
import ru.DmN.pht.base.parser.ParsingContext
import ru.DmN.pht.base.parser.parsers.SimpleNP
import ru.DmN.pht.std.math.ast.NodeEquals

object NPEquals : SimpleNP<NodeEquals>() {
    override fun parse(parser: Parser, ctx: ParsingContext, operationToken: Token): NodeEquals =
        super.parse(parser, ctx) {
            NodeEquals(
                operationToken, when (operationToken.text!!) {
                    "=" -> NodeEquals.Operation.EQ
                    "!=" -> NodeEquals.Operation.NE
                    "<" -> NodeEquals.Operation.LT
                    "<=" -> NodeEquals.Operation.LE
                    ">" -> NodeEquals.Operation.GT
                    ">=" -> NodeEquals.Operation.GE
                    else -> throw RuntimeException()
                }, it
            )
        }
}