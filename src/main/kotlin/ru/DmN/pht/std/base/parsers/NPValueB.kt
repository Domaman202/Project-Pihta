package ru.DmN.pht.std.base.parsers

import ru.DmN.pht.base.lexer.Token
import ru.DmN.pht.base.Parser
import ru.DmN.pht.base.lexer.Token.Type
import ru.DmN.pht.base.parser.ParsingContext
import ru.DmN.pht.base.parser.ast.Node
import ru.DmN.pht.base.parser.parsers.NodeParser
import ru.DmN.pht.std.base.ast.NodeValue
import ru.DmN.pht.std.base.ast.NodeValue.Type.*

object NPValueB : NodeParser() {
    override fun parse(parser: Parser, ctx: ParsingContext, operationToken: Token): Node? =
        operationToken.text!!.let { text ->
            NodeValue(
                Token(operationToken.line, operationToken.type, "value"), when (operationToken.type) {
                    Type.OPERATION -> {
                        parser.tokens.push(operationToken)
                        return parser.get(ctx, "get-or-name!")!!.parse(parser, ctx, Token(operationToken.line, Type.OPERATION, "get-or-name!"))
                    }

                    Type.PRIMITIVE -> PRIMITIVE
                    Type.CLASS -> {
                        if (text.contains("[/#]".toRegex())) {
                            parser.tokens.push(operationToken)
                            return parser.get(ctx, "get!")!!.parse(parser, ctx, Token(operationToken.line, Type.OPERATION, "get_"))
                        } else CLASS
                    }
                    Type.NAMING -> NAMING

                    Type.NIL -> NIL
                    Type.BOOLEAN -> BOOLEAN
                    Type.CHAR -> CHAR
                    Type.INTEGER -> INT
                    Type.LONG -> LONG
                    Type.FLOAT -> FLOAT
                    Type.DOUBLE -> DOUBLE
                    Type.STRING -> STRING
                    else -> throw RuntimeException()
                }, if (operationToken.type == Type.CLASS) {
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