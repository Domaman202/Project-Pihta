package ru.DmN.pht.std.parser.utils

import ru.DmN.pht.ctx.ContextKeys
import ru.DmN.siberia.Parser
import ru.DmN.siberia.ast.Node
import ru.DmN.siberia.ctx.IContextCollection
import ru.DmN.siberia.lexer.Token
import ru.DmN.siberia.lexer.Token.DefaultType.*
import ru.DmN.siberia.parser.ctx.ParsingContext
import ru.DmN.siberia.parser.utils.parseMCall
import ru.DmN.siberia.parser.utils.parseMacro
import ru.DmN.siberia.parser.utils.parseValn
import ru.DmN.siberia.parser.utils.parseValue
import java.util.*

fun Parser.phtParseNode(ctx: ParsingContext): Node? {
    val startToken = nextToken() ?: return null
    return when (startToken.type) {
        OPEN_BRACKET -> pnb {
            val token = nextToken()!!
            when (token.type) {
                OPEN_BRACKET -> {
                    tokens.push(token)
                    parseProgn(ctx, token)
                }
                OPEN_CBRACKET -> parseValn(ctx, startToken)
                OPERATION -> {
                    val parser = get(ctx, token.text!!)
                    if (parser == null) {
                        if (token.text == "page-list")
                            println()
                        tokens.push(token)
                        parseMacro(ctx, Token(token.line, OPERATION, "macro"))
                    } else parser.parse(this, ctx, token)
                }
                NAMING -> {
                    tokens.push(token)
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
            tokens.push(startToken)
            null
        }
    }
}

fun Parser.parseProgn(ctx: ParsingContext, token: Token) =
    get(ctx, "progn")!!.parse(this, ctx, token)

fun IContextCollection<*>.clearMacros() {
    this.contexts.remove(ContextKeys.MACROS)
}

var IContextCollection<*>.macros
    set(value) { this.contexts[ContextKeys.MACROS] = value }
    get() = this.contexts[ContextKeys.MACROS] as Stack<UUID>