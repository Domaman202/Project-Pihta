package ru.DmN.pht.parser

import ru.DmN.pht.module.utils.ModulesProvider
import ru.DmN.pht.parser.utils.parseMCall
import ru.DmN.pht.parser.utils.parseMacro
import ru.DmN.siberia.ast.Node
import ru.DmN.siberia.lexer.Lexer
import ru.DmN.siberia.lexer.Token
import ru.DmN.siberia.lexer.Token.DefaultType.*
import ru.DmN.siberia.parser.Parser
import ru.DmN.siberia.parser.ParserImpl
import ru.DmN.siberia.parser.ctx.ParsingContext
import ru.DmN.siberia.parser.utils.*
import java.util.*

class ParserImpl(lexer: Lexer, mp: ModulesProvider, tokens: Stack<Token?>) : ParserImpl(lexer, mp, tokens) {
    constructor(parser: Parser) : this(parser.lexer, parser.mp, parser.tokens)

    override fun parseNode(ctx: ParsingContext): Node? {
        val startToken = nextToken() ?: return null
        return when (startToken.type) {
            OPEN_BRACKET -> pnb {
                val token = nextToken()!!
                when (token.type) {
                    OPEN_BRACKET -> {
                        pushToken(token)
                        parseProgn(ctx, token)
                    }
                    OPEN_CBRACKET -> parseValn(ctx, startToken)
                    OPERATION -> {
                        val parser = get(ctx, token.text!!)
                        if (parser == null) {
                            pushToken(token)
                            parseMacro(ctx, Token(token.line, OPERATION, "macro"))
                        } else parser.parse(this, ctx, token)
                    }
                    NAMING -> {
                        pushToken(token)
                        parseMCall(ctx, token)
                    }
                    else -> throw RuntimeException()
                }
            }
            OPEN_CBRACKET -> parseValn(ctx, startToken)
            OPERATION,
            PRIMITIVE,
            CLASS,
            NAMING,
            NIL,
            STRING,
            INTEGER,
            FLOAT,
            DOUBLE,
            BOOLEAN -> parseValue(ctx, startToken)
            else -> {
                pushToken(startToken)
                null
            }
        }
    }
}