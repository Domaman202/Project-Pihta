package ru.DmN.pht.std.math.parsers

import ru.DmN.pht.base.lexer.Token
import ru.DmN.pht.base.Parser
import ru.DmN.pht.base.parser.parsers.SimpleNodeParser
import ru.DmN.pht.std.math.ast.NodeEquals

object NPEquals : SimpleNodeParser<NodeEquals>() {
    override fun parse(parser: Parser, operationToken: Token): NodeEquals =
        super.parse(parser) {
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