package ru.DmN.pht.base

import ru.DmN.pht.base.lexer.Lexer
import ru.DmN.pht.base.lexer.Token
import ru.DmN.pht.base.lexer.Token.Type.*
import ru.DmN.pht.base.parser.ParsingContext
import ru.DmN.pht.base.parser.ast.Node
import ru.DmN.pht.base.parser.parsers.INodeParser
import ru.DmN.pht.base.utils.getRegex
import java.util.*

class Parser(val lexer: Lexer) {
    val tokens = Stack<Token>()

    constructor(code: String) : this(Lexer(code))

    fun parseNode(ctx: ParsingContext): Node? {
        val startToken = nextToken() ?: return null
        return when (startToken.type) {
            OPEN_BRACKET -> pnb {
                val operationToken = nextToken()!!
                when (operationToken.type) {
                    OPEN_BRACKET -> {
                        tokens.push(operationToken)
                        parseProgn(ctx, operationToken)
                    }
                    OPEN_CBRACKET -> parseValn(ctx, startToken)
                    OPERATION -> {
                        val parser = get(ctx, operationToken.text!!)
                        if (parser == null) {
                            tokens.push(operationToken)
                            parseMacro(ctx, Token(operationToken.line, OPERATION, "macro!"))
                        } else parser.parse(this, ctx, operationToken)
                    }
                    NAMING -> {
                        tokens.push(operationToken)
                        parseMCall(ctx, operationToken)
                    }
                    else -> throw RuntimeException()
                }
            }
            OPEN_CBRACKET -> parseValn(ctx, startToken)
            OPERATION, PRIMITIVE, CLASS, NAMING, NIL, STRING, INTEGER, FLOAT, DOUBLE, BOOLEAN -> parseValue(ctx, startToken)
            else -> {
                tokens.push(startToken)
                null
            }
        }
    }

    fun parseProgn(ctx: ParsingContext, token: Token) =
        get(ctx, "progn")!!.parse(this, ctx, token)
    fun parseValn(ctx: ParsingContext, token: Token) =
        get(ctx, "valn!")!!.parse(this, ctx, token)
    fun parseMacro(ctx: ParsingContext, token: Token) =
        get(ctx, "macro!")!!.parse(this, ctx, token)
    fun parseMCall(ctx: ParsingContext, token: Token) =
        get(ctx, "mcall!")!!.parse(this, ctx, token)
    fun parseValue(ctx: ParsingContext, token: Token) =
        get(ctx, "value!")!!.parse(this, ctx, token)!!
    fun get(ctx: ParsingContext, name: String): INodeParser? {
        val i = name.lastIndexOf('/')
        return if (i < 1) {
            ctx.loadedModules.forEach { it -> it.parsers.getRegex(name)?.let { return it } }
            null
        } else {
            val module = name.substring(0, i)
            ctx.loadedModules.find { it.name == module }?.parsers?.getRegex(name.substring(i + 1))
        }
    }

    private inline fun <T> pnb(body: () -> T): T = body.invoke().apply { tryClose() }

    private fun tryClose() {
        val token = nextToken()
        if (token?.type != CLOSE_BRACKET) {
            tokens.push(token)
        }
    }

    fun nextToken(): Token? {
        return if (tokens.empty())
            lexer.next()
        else tokens.pop()
    }
}