package ru.DmN.pht.base

import ru.DmN.pht.base.ast.Node
import ru.DmN.pht.base.lexer.Lexer
import ru.DmN.pht.base.lexer.Token
import ru.DmN.pht.base.lexer.Token.Type.CLOSE_BRACKET
import ru.DmN.pht.base.parser.ctx.ParsingContext
import ru.DmN.pht.base.parser.utils.baseParseNode
import ru.DmN.pht.base.parsers.INodeParser
import ru.DmN.pht.base.utils.getRegex
import java.util.*

class Parser(val lexer: Lexer, var parseNode: Parser.(ctx: ParsingContext) -> Node?) {
    val tokens = Stack<Token>()

    constructor(code: String) : this(Lexer(code), { baseParseNode(it) })

    fun parseNode(ctx: ParsingContext) =
        parseNode(this, ctx)

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

    inline fun <T> pnb(body: () -> T): T = body.invoke().apply { tryClose() }

    fun tryClose() {
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