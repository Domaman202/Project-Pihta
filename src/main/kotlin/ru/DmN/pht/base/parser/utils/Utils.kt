package ru.DmN.pht.base.parser.utils

import ru.DmN.pht.base.Parser
import ru.DmN.pht.base.ast.Node
import ru.DmN.pht.base.lexer.Token
import ru.DmN.pht.base.parser.ctx.ParsingContext
import ru.DmN.pht.std.parser.parseProgn
import ru.DmN.pht.std.parser.parseValn
import ru.DmN.pht.std.parser.parseValue
import java.util.Stack

fun Parser.baseParseNode(ctx: ParsingContext): Node? {
    val startToken = nextToken() ?: return null
    return when (startToken.type) {
        Token.Type.OPEN_BRACKET -> pnb {
            val operationToken = nextToken()!!
            when (operationToken.type) {
                Token.Type.OPEN_BRACKET -> {
                    tokens.push(operationToken)
                    parseProgn(ctx, operationToken)
                }
                Token.Type.OPERATION -> get(ctx, operationToken.text!!)!!.parse(this, ctx, operationToken)
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

var ParsingContext.parsersPool
    set(value) { this.contexts["base/pp"] = value }
    get() = this.contexts["base/pp"] as Stack<Parser.(ctx: ParsingContext) -> Node?>