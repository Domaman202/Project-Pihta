package ru.DmN.pht.base

import ru.DmN.pht.base.lexer.*
import ru.DmN.pht.base.parser.ast.Node
import ru.DmN.pht.base.parser.parsers.NPNodesList
import ru.DmN.pht.base.parser.parsers.NPUse
import ru.DmN.pht.base.parser.parsers.NodeParser
import ru.DmN.pht.std.utils.Module
import java.util.*

class Parser(val lexer: Lexer) {
    val modules: MutableList<Module> = ArrayList()
    val parsers: MutableMap<String, NodeParser> = DEFAULT_PARSERS.toMutableMap()
    val tokens = Stack<Token>()

    fun parseNode(): Node? {
        val startToken = nextToken() ?: return null
        return when (startToken.type) {
            Token.Type.OPEN_BRACKET -> pnb {
                val operationToken = nextToken()!!
                when (operationToken.type) {
                    Token.Type.OPEN_BRACKET -> {
                        tokens.push(operationToken)
                        parsers["nslist"]!!.parse(this, operationToken)
                    }
                    Token.Type.OPERATION -> parsers[operationToken.text!!]!!.parse(this, operationToken)
                    Token.Type.NAMING -> {
                        tokens.push(operationToken)
                        parsers["mcall_"]!!.parse(this, operationToken)
                    }
                    else -> throw RuntimeException()
                }
            }

            Token.Type.OPERATION, Token.Type.PRIMITIVE, Token.Type.CLASS, Token.Type.NAMING, Token.Type.NIL, Token.Type.STRING, Token.Type.NUMBER, Token.Type.BOOLEAN -> parseValue(startToken)
            else -> throw RuntimeException()
        }
    }

    fun parseValue(valueToken: Token): Node = parsers["value_"]!!.parse(this, valueToken)!!

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

    companion object {
        private val DEFAULT_PARSERS: Map<String, NodeParser>

        init {
            DEFAULT_PARSERS = HashMap()
            // use
            DEFAULT_PARSERS["use"] = NPUse()
            // Блок
            DEFAULT_PARSERS["nslist"] = NPNodesList()
        }
    }
}