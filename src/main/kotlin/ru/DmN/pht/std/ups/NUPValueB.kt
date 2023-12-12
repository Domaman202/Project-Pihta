package ru.DmN.pht.std.ups

import ru.DmN.siberia.Parser
import ru.DmN.siberia.ast.Node
import ru.DmN.siberia.lexer.Token
import ru.DmN.siberia.parser.ctx.ParsingContext
import ru.DmN.pht.std.ast.NodeValue
import ru.DmN.siberia.lexer.Token.DefaultType.*
import ru.DmN.siberia.utils.INUP

object NUPValueB : INUP<Node, Node> {
    override fun parse(parser: Parser, ctx: ParsingContext, operationToken: Token): Node? =
        operationToken.text!!.let { text ->
            NodeValue(
                Token(operationToken.line, operationToken.type, "value"), when (operationToken.type) {
                    OPERATION -> {
                        parser.tokens.push(operationToken)
                        return parser.get(ctx, "get-or-name!")!!.parse(parser, ctx, Token.operation(operationToken.line, "get-or-name!"))
                    }

                    PRIMITIVE   -> NodeValue.Type.PRIMITIVE
                    CLASS       -> {
                        if (text.contains("[/#]".toRegex())) {
                            parser.tokens.push(operationToken)
                            return parser.get(ctx, "get")!!.parse(parser, ctx, Token.operation(operationToken.line, "get_"))
                        } else NodeValue.Type.CLASS
                    }

                    NAMING  -> NodeValue.Type.NAMING
                    NIL     -> NodeValue.Type.NIL
                    BOOLEAN -> NodeValue.Type.BOOLEAN
                    CHAR    -> NodeValue.Type.CHAR
                    INTEGER -> NodeValue.Type.INT
                    LONG    -> NodeValue.Type.LONG
                    FLOAT   -> NodeValue.Type.FLOAT
                    DOUBLE  -> NodeValue.Type.DOUBLE
                    STRING  -> NodeValue.Type.STRING
                    else -> throw RuntimeException()
                }, if (operationToken.type == CLASS) {
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