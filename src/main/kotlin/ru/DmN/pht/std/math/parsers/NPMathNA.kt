package ru.DmN.pht.std.math.parsers

import ru.DmN.pht.base.lexer.Token
import ru.DmN.pht.base.Parser
import ru.DmN.pht.base.parser.ParsingContext
import ru.DmN.pht.base.parser.parsers.SimpleNP
import ru.DmN.pht.std.math.ast.NodeMathNA

object NPMathNA : SimpleNP<NodeMathNA>() {
    override fun parse(parser: Parser, ctx: ParsingContext, operationToken: Token): NodeMathNA =
        super.parse(parser, ctx) {
            NodeMathNA(
                operationToken, when (operationToken.text!!) {
                    "+" -> NodeMathNA.Operation.ADD
                    "-" -> NodeMathNA.Operation.SUB
                    "*" -> NodeMathNA.Operation.MUL
                    "/" -> NodeMathNA.Operation.DIV
                    else -> throw RuntimeException()
                }, it
            )
        }
}