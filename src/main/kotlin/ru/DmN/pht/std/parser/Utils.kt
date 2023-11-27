package ru.DmN.pht.std.parser

import ru.DmN.siberia.Parser
import ru.DmN.siberia.ast.Node
import ru.DmN.siberia.lexer.Token
import ru.DmN.siberia.parser.ctx.ParsingContext
import ru.DmN.siberia.parser.utils.*
import ru.DmN.siberia.utils.IContextCollection
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

fun IContextCollection<*>.clearMacros() {
    this.contexts.remove("pht/macro")
}

var IContextCollection<*>.macros
    set(value) { this.contexts["pht/macro"] = value }
    get() = this.contexts["pht/macro"] as Stack<UUID>