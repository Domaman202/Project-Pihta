package ru.DmN.pht.std.base.parsers

import ru.DmN.pht.base.Parser
import ru.DmN.pht.base.lexer.Token
import ru.DmN.pht.base.parser.ParsingContext
import ru.DmN.pht.base.parser.ast.Node
import ru.DmN.pht.base.parser.parsers.INodeParser
import ru.DmN.pht.std.base.ast.NodeValue
import ru.DmN.pht.std.base.ast.NodeValue.Type.*

object NPValueA : INodeParser {
    override fun parse(parser: Parser, ctx: ParsingContext, operationToken: Token): Node =
        parser.nextToken()!!.let { value ->
            value.text!!.let { text ->
                NodeValue(
                    operationToken, when (value.type) {
                        Token.Type.OPERATION -> if (text == "nil") NIL else throw RuntimeException()
                        Token.Type.CLASS -> CLASS
                        Token.Type.NAMING -> NAMING
                        Token.Type.STRING -> STRING
                        Token.Type.INTEGER -> INT
                        Token.Type.LONG -> LONG
                        Token.Type.FLOAT -> FLOAT
                        Token.Type.DOUBLE -> DOUBLE
                        else -> throw RuntimeException()
                    }, if (value.type == Token.Type.CLASS) text else text
                )
            }
        }
}