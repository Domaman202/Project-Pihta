package ru.DmN.pht.std.parsers

import ru.DmN.pht.base.Parser
import ru.DmN.pht.base.lexer.Token
import ru.DmN.pht.base.lexer.Token.Type.*
import ru.DmN.pht.base.lexer.isNaming
import ru.DmN.pht.base.lexer.isOperation
import ru.DmN.pht.base.parser.ParsingContext
import ru.DmN.pht.base.ast.Node
import ru.DmN.pht.base.parsers.INodeParser
import ru.DmN.pht.std.ast.NodeFMGet
import ru.DmN.pht.std.ast.NodeGetOrName
import ru.DmN.pht.std.ast.NodeValue

object NPGet : INodeParser {
    override fun parse(parser: Parser, ctx: ParsingContext, token: Token): Node {
        val nameToken = parser.nextToken()!!
        return when (nameToken.type) {
            CLASS -> process(
                token,
                nameToken.text!!,
                static = true,
                klass = true
            )
            OPERATION -> process(
                token,
                nameToken.text!!,
                static = false,
                klass = false
            )
            OPEN_BRACKET -> {
                parser.tokens.push(nameToken)
                return NodeFMGet(
                    token,
                    parser.parseNode(ctx)!!,
                    parser.nextToken()!!
                        .let { if (it.isOperation() || it.isNaming()) it else throw RuntimeException() }.text!!,
                    false
                )
            }

            else -> throw RuntimeException()
        }
    }

    private fun process(token: Token, name: String, static: Boolean, klass: Boolean): Node {
        val parts = name.split("/", "#") as MutableList<String>
        return process(token, parts, parts.size, static, klass)
    }

    private fun process(token: Token, parts: List<String>, i: Int, static: Boolean, clazz: Boolean): Node {
        val j = i - 1
        return if (j == 0) {
            if (clazz)
                NodeValue.of(token.line, NodeValue.Type.CLASS, parts[0])
            else NodeGetOrName(token, parts[0], static)
        } else {
            val isStatic = static && j == 1
            NodeFMGet(
                Token(token.line, OPERATION, "fget!",),
                process(token, parts, j, static, clazz),
                parts[j],
                isStatic
            )
        }
    }
}