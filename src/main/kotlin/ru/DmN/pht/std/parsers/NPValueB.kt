package ru.DmN.pht.std.parsers

import ru.DmN.pht.base.lexer.Token
import ru.DmN.pht.base.Parser
import ru.DmN.pht.base.parser.ast.Node
import ru.DmN.pht.base.parser.parsers.NodeParser
import ru.DmN.pht.std.ast.NodeValue

object NPValueB : NodeParser() {
    override fun parse(parser: Parser, operationToken: Token): Node? =
        operationToken.text!!.let { text ->
            NodeValue(
                Token(operationToken.line, operationToken.type, "value"), when (operationToken.type) {
                    Token.Type.OPERATION -> {
                        parser.tokens.push(operationToken)
                        return parser.parsers["get-or-name"]!!.parse(parser, Token(operationToken.line, Token.Type.OPERATION, "get-or-name"))
                    }

                    Token.Type.PRIMITIVE -> NodeValue.Type.PRIMITIVE
                    Token.Type.CLASS -> {
                        if (text.contains("[/#]".toRegex())) {
                            parser.tokens.push(operationToken)
                            return parser.parsers["get_"]!!.parse(parser, Token(operationToken.line, Token.Type.OPERATION, "get_"))
                        } else NodeValue.Type.CLASS
                    }

                    Token.Type.NIL -> NodeValue.Type.NIL
                    Token.Type.NAMING -> NodeValue.Type.NAMING
                    Token.Type.STRING -> NodeValue.Type.STRING
                    Token.Type.NUMBER -> if (text.contains(".")) NodeValue.Type.DOUBLE else NodeValue.Type.INT
                    Token.Type.BOOLEAN -> NodeValue.Type.BOOLEAN
                    else -> throw RuntimeException()
                }, if (operationToken.type == Token.Type.CLASS) {
                    text.indexOf('/').let {
                        val i = text.indexOf('#')
                        if (it == -1) i
                        else if (i == -1) it
                        else it.coerceAtLeast(i)
                    }.let { if (it == -1) text else text.substring(0, it) }
                } else text
            )
        }
}