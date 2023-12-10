package ru.DmN.pht.std.parser.utils

import ru.DmN.siberia.Parser
import ru.DmN.siberia.ast.Node
import ru.DmN.siberia.lexer.Token
import ru.DmN.siberia.parser.ctx.ParsingContext
import ru.DmN.siberia.parser.utils.parseMCall
import ru.DmN.siberia.parser.utils.parseMacro
import ru.DmN.siberia.parser.utils.parseValn
import ru.DmN.siberia.parser.utils.parseValue
import ru.DmN.siberia.utils.IContextCollection
import java.util.*

fun Parser.phtParseNode(ctx: ParsingContext): Node? {
    val startToken = nextToken() ?: return null
    return when (startToken.type) {
        Token.Type.OPEN_BRACKET -> pnb {
            val token = nextToken()!!
            when (token.type) {
                Token.Type.OPEN_BRACKET -> {
                    tokens.push(token)
                    parseProgn(ctx, token)
                }
                Token.Type.OPEN_CBRACKET -> parseValn(ctx, startToken)
                Token.Type.OPERATION -> {
                    val parser = get(ctx, token.text!!)
                    if (parser == null) {
                        tokens.push(token)
                        parseMacro(ctx, Token(token.line, Token.Type.OPERATION, "macro"))
                    } else parser.parse(this, ctx, token)
                }
                Token.Type.NAMING -> {
                    tokens.push(token)
                    parseMCall(ctx, token)
                }
                else -> throw RuntimeException()
            }
        }
        Token.Type.OPEN_CBRACKET -> parseValn(ctx, startToken)
        Token.Type.OPERATION, Token.Type.PRIMITIVE, Token.Type.CLASS, Token.Type.NAMING, Token.Type.NIL, Token.Type.STRING, Token.Type.INTEGER, Token.Type.FLOAT, Token.Type.DOUBLE, Token.Type.BOOLEAN -> parseValue(ctx, startToken)
        else -> {
            tokens.push(startToken)
            null
        }
    }
}

fun Parser.parseProgn(ctx: ParsingContext, token: Token) =
    get(ctx, "progn!")!!.parse(this, ctx, token)

fun IContextCollection<*>.clearMacros() {
    this.contexts.remove("pht/macro")
}

var IContextCollection<*>.macros
    set(value) { this.contexts["pht/macro"] = value }
    get() = this.contexts["pht/macro"] as Stack<UUID>