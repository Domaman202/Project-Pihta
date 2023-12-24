package ru.DmN.pht.std.parsers

import ru.DmN.pht.std.ast.NodeValue
import ru.DmN.pht.std.node.NodeTypes
import ru.DmN.siberia.Parser
import ru.DmN.siberia.ast.Node
import ru.DmN.siberia.lexer.Token
import ru.DmN.siberia.node.NodeInfoImpl
import ru.DmN.siberia.parser.ctx.ParsingContext
import ru.DmN.siberia.parsers.INodeParser

object NPValueB : INodeParser {
    override fun parse(parser: Parser, ctx: ParsingContext, token: Token): Node? =
        token.text!!.let { text ->
            NodeValue(
                NodeInfoImpl.of(NodeTypes.VALUE, ctx, token),
                when (token.type) {
                    Token.DefaultType.OPERATION -> {
                        parser.tokens.push(token)
                        return parser.get(ctx, "get-or-name!")!!.parse(parser, ctx, Token.operation(token.line, "get-or-name!"))
                    }

                    Token.DefaultType.PRIMITIVE -> NodeValue.Type.PRIMITIVE
                    Token.DefaultType.CLASS     -> {
                        if (text.contains("[/#]".toRegex())) {
                            parser.tokens.push(token)
                            return parser.get(ctx, "get")!!.parse(parser, ctx, Token.operation(token.line, "get_"))
                        } else NodeValue.Type.CLASS
                    }
                    Token.DefaultType.CLASS_WITH_GEN -> NodeValue.Type.CLASS_WITH_GEN

                    Token.DefaultType.NAMING  -> NodeValue.Type.NAMING
                    Token.DefaultType.NIL     -> NodeValue.Type.NIL
                    Token.DefaultType.BOOLEAN -> NodeValue.Type.BOOLEAN
                    Token.DefaultType.CHAR    -> NodeValue.Type.CHAR
                    Token.DefaultType.INTEGER -> NodeValue.Type.INT
                    Token.DefaultType.LONG    -> NodeValue.Type.LONG
                    Token.DefaultType.FLOAT   -> NodeValue.Type.FLOAT
                    Token.DefaultType.DOUBLE  -> NodeValue.Type.DOUBLE
                    Token.DefaultType.STRING  -> NodeValue.Type.STRING
                    else -> throw RuntimeException()
                }, if (token.type == Token.DefaultType.CLASS) {
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