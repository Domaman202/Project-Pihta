package ru.DmN.pht.std.parsers

import ru.DmN.pht.base.lexer.Token
import ru.DmN.pht.base.Parser
import ru.DmN.pht.base.parser.ast.Node
import ru.DmN.pht.base.parser.parsers.NodeParser
import ru.DmN.pht.std.ast.NodeValue

object NPValueA : NodeParser() {
    override fun parse(parser: Parser, operationToken: Token): Node =
        parser.nextToken()!!.let { value ->
            value.text!!.let { text ->
                NodeValue(
                    operationToken, when (value.type) {
                        Token.Type.OPERATION -> if (text == "nil") NodeValue.Type.NIL else throw RuntimeException()
                        Token.Type.CLASS -> NodeValue.Type.CLASS
                        Token.Type.NAMING -> NodeValue.Type.NAMING
                        Token.Type.STRING -> NodeValue.Type.STRING
                        Token.Type.NUMBER -> if (text.contains(".")) NodeValue.Type.DOUBLE else NodeValue.Type.INT
                        else -> throw RuntimeException()
                    }, if (value.type == Token.Type.CLASS) text else text
                )
            }
        }
}