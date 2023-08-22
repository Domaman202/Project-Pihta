package ru.DmN.pht.base

import ru.DmN.pht.base.lexer.*
import ru.DmN.pht.base.parser.ParsingContext
import ru.DmN.pht.base.parser.ast.Node
import ru.DmN.pht.base.parser.parsers.NodeParser
import java.util.*

class Parser(val lexer: Lexer) {
    val tokens = Stack<Token>()

    fun parseNode(ctx: ParsingContext): Node? {
        val startToken = nextToken() ?: return null
        return when (startToken.type) {
            Token.Type.OPEN_BRACKET -> pnb {
                val operationToken = nextToken()!!
                when (operationToken.type) {
                    Token.Type.OPEN_BRACKET -> {
                        tokens.push(operationToken)
                        parseProgn(ctx, operationToken)
                    }
                    Token.Type.OPEN_CBRACKET -> parseValn(ctx, startToken)
                    Token.Type.OPERATION -> {
                        val parser = get(ctx, operationToken.text!!)
                        if (parser == null) {
                            tokens.push(operationToken)
                            parseMacro(ctx, Token(operationToken.line, Token.Type.OPERATION, "macro!"))
                        } else parser.parse(this, ctx, operationToken)
                    }
                    Token.Type.NAMING -> {
                        tokens.push(operationToken)
                        parseMCall(ctx, operationToken)
                    }
                    else -> throw RuntimeException()
                }
            }
            Token.Type.OPEN_CBRACKET -> parseValn(ctx, startToken)
            Token.Type.OPERATION, Token.Type.PRIMITIVE, Token.Type.CLASS, Token.Type.NAMING, Token.Type.NIL, Token.Type.STRING, Token.Type.NUMBER, Token.Type.BOOLEAN -> parseValue(ctx, startToken)
            else -> {
                tokens.push(startToken)
                null
            }
        }
    }

    fun parseProgn(ctx: ParsingContext, token: Token) =
        get(ctx, "progn")!!.parse(this, ctx, token)
    fun parseValn(ctx: ParsingContext, token: Token) =
        get(ctx, "valn")!!.parse(this, ctx, token)
    fun parseMacro(ctx: ParsingContext, token: Token) =
        get(ctx, "macro!")!!.parse(this, ctx, token)
    fun parseMCall(ctx: ParsingContext, token: Token) =
        get(ctx, "mcall!")!!.parse(this, ctx, token)
    fun parseValue(ctx: ParsingContext, token: Token) =
        get(ctx, "value!")!!.parse(this, ctx, token)!!
    fun get(ctx: ParsingContext, name: String): NodeParser? {
        val i = name.lastIndexOf('/')
        return if (i == -1) {
            ctx.modules.forEach { it -> it.parsers[name]?.let { return it } }
            null
        } else {
            val module = name.substring(0, i)
            ctx.modules.find { it.name == module }?.parsers?.get(name.substring(i + 1))
        }
    }

    private inline fun <T> pnb(body: () -> T): T = body.invoke().apply { tryClose() }

    private fun tryClose() {
        val token = nextToken()
        if (token?.type != Token.Type.CLOSE_BRACKET) {
            tokens.push(token)
        }
    }

    fun nextToken(): Token? {
        return if (tokens.empty())
            lexer.next()
        else tokens.pop()
    }
}