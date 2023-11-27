package ru.DmN.pht.std.parser

import ru.DmN.pht.base.Parser
import ru.DmN.pht.base.ast.Node
import ru.DmN.pht.base.lexer.Token
import ru.DmN.pht.base.parser.ctx.ParsingContext
import ru.DmN.pht.base.utils.IContextCollection
import java.util.Stack
import java.util.UUID

fun Parser.phtParseNode(ctx: ParsingContext): Node? {
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
                        parseMacro(ctx, Token(operationToken.line, Token.Type.OPERATION, "macro"))
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
        Token.Type.OPERATION, Token.Type.PRIMITIVE, Token.Type.CLASS, Token.Type.NAMING, Token.Type.NIL, Token.Type.STRING, Token.Type.INTEGER, Token.Type.FLOAT, Token.Type.DOUBLE, Token.Type.BOOLEAN -> parseValue(ctx, startToken)
        else -> {
            tokens.push(startToken)
            null
        }
    }
}

fun Parser.parseProgn(ctx: ParsingContext, token: Token) =
    get(ctx, "progn")!!.parse(this, ctx, token)
fun Parser.parseValn(ctx: ParsingContext, token: Token) =
    get(ctx, "valn!")!!.parse(this, ctx, token)
fun Parser.parseMacro(ctx: ParsingContext, token: Token) =
    get(ctx, "macro")!!.parse(this, ctx, token)
fun Parser.parseMCall(ctx: ParsingContext, token: Token) =
    get(ctx, "mcall!")!!.parse(this, ctx, token)
fun Parser.parseValue(ctx: ParsingContext, token: Token) =
    get(ctx, "value!")!!.parse(this, ctx, token)!!

fun IContextCollection<*>.clearMacros() {
    this.contexts.remove("pht/macro")
}

var IContextCollection<*>.macros
    set(value) { this.contexts["pht/macro"] = value }
    get() = this.contexts["pht/macro"] as Stack<UUID>