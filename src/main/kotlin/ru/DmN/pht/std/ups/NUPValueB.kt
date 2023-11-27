package ru.DmN.pht.std.ups

import ru.DmN.siberia.Parser
import ru.DmN.siberia.ast.Node
import ru.DmN.siberia.lexer.Token
import ru.DmN.siberia.parser.ctx.ParsingContext
import ru.DmN.pht.std.ast.NodeValue
import ru.DmN.pht.std.processors.INodeUniversalProcessor

object NUPValueB : INodeUniversalProcessor<Node, Node> {
    override fun parse(parser: Parser, ctx: ParsingContext, operationToken: Token): Node? =
        operationToken.text!!.let { text ->
            NodeValue(
                Token(operationToken.line, operationToken.type, "value"), when (operationToken.type) {
                    Token.Type.OPERATION -> {
                        parser.tokens.push(operationToken)
                        return parser.get(ctx, "get-or-name!")!!.parse(parser, ctx, Token(operationToken.line, Token.Type.OPERATION, "get-or-name!"))
                    }

                    Token.Type.PRIMITIVE -> NodeValue.Type.PRIMITIVE
                    Token.Type.CLASS -> {
                        if (text.contains("[/#]".toRegex())) {
                            parser.tokens.push(operationToken)
                            return parser.get(ctx, "get")!!.parse(parser, ctx, Token(operationToken.line, Token.Type.OPERATION, "get_"))
                        } else NodeValue.Type.CLASS
                    }
                    Token.Type.NAMING -> NodeValue.Type.NAMING

                    Token.Type.NIL -> NodeValue.Type.NIL
                    Token.Type.BOOLEAN -> NodeValue.Type.BOOLEAN
                    Token.Type.CHAR -> NodeValue.Type.CHAR
                    Token.Type.INTEGER -> NodeValue.Type.INT
                    Token.Type.LONG -> NodeValue.Type.LONG
                    Token.Type.FLOAT -> NodeValue.Type.FLOAT
                    Token.Type.DOUBLE -> NodeValue.Type.DOUBLE
                    Token.Type.STRING -> NodeValue.Type.STRING
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