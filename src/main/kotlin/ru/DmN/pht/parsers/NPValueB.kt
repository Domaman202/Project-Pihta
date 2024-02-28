package ru.DmN.pht.parsers

import ru.DmN.pht.ast.NodeValue
import ru.DmN.pht.ast.NodeValue.Type.*
import ru.DmN.pht.utils.node.NodeTypes.VALUE
import ru.DmN.siberia.Parser
import ru.DmN.siberia.ast.Node
import ru.DmN.siberia.lexer.Token
import ru.DmN.siberia.lexer.Token.DefaultType
import ru.DmN.siberia.parser.ctx.ParsingContext
import ru.DmN.siberia.parsers.INodeParser
import ru.DmN.siberia.utils.node.INodeInfo

object NPValueB : INodeParser {
    override fun parse(parser: Parser, ctx: ParsingContext, token: Token): Node? =
        token.text!!.let { text ->
            NodeValue(
                INodeInfo.of(VALUE, ctx, token),
                when (token.type) {
                    DefaultType.OPERATION -> {
                        parser.tokens.push(token)
                        return parser.get(ctx, "get-or-name!")!!.parse(parser, ctx, Token.operation(token.line, "get-or-name!"))
                    }

                    DefaultType.PRIMITIVE -> PRIMITIVE
                    DefaultType.CLASS     -> {
                        if (text.contains("[/#]".toRegex()))
                            return NPGet.parse(INodeInfo.of(VALUE), token.text!!, mutableListOf(), static = true, klass = true)
                        else CLASS
                    }

                    DefaultType.CLASS_WITH_GEN -> CLASS_WITH_GEN
                    DefaultType.NAMING  -> NAMING
                    DefaultType.NIL     -> NIL
                    DefaultType.BOOLEAN -> BOOLEAN
                    DefaultType.CHAR    -> CHAR
                    DefaultType.INTEGER -> INT
                    DefaultType.LONG    -> LONG
                    DefaultType.FLOAT   -> FLOAT
                    DefaultType.DOUBLE  -> DOUBLE
                    DefaultType.STRING  -> STRING
                    else -> throw RuntimeException()
                }, if (token.type == DefaultType.CLASS) {
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