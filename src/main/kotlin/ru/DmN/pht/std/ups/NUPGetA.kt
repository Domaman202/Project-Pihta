package ru.DmN.pht.std.ups

import ru.DmN.siberia.Parser
import ru.DmN.siberia.ast.Node
import ru.DmN.siberia.lexer.Token
import ru.DmN.siberia.lexer.isNaming
import ru.DmN.siberia.lexer.isOperation
import ru.DmN.siberia.parser.ctx.ParsingContext
import ru.DmN.pht.std.ast.NodeFMGet
import ru.DmN.pht.std.ast.NodeGetOrName
import ru.DmN.pht.std.ast.NodeValue
import ru.DmN.pht.std.processors.INodeUniversalProcessor

object NUPGetA : INodeUniversalProcessor<Node, Node> {
    override fun parse(parser: Parser, ctx: ParsingContext, token: Token): Node {
        val nameToken = parser.nextToken()!!
        return when (nameToken.type) {
            Token.Type.CLASS -> parse(
                token,
                nameToken.text!!,
                static = true,
                klass = true
            )
            Token.Type.OPERATION -> parse(
                token,
                nameToken.text!!,
                static = false,
                klass = false
            )
            Token.Type.OPEN_BRACKET -> {
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

    private fun parse(token: Token, name: String, static: Boolean, klass: Boolean): Node {
        val parts = name.split("/", "#") as MutableList<String>
        return parse(token, parts, parts.size, static, klass)
    }

    private fun parse(token: Token, parts: List<String>, i: Int, static: Boolean, clazz: Boolean): Node {
        val j = i - 1
        return if (j == 0) {
            if (clazz)
                NodeValue(Token(token.line, Token.Type.OPERATION, "value"), NodeValue.Type.CLASS, parts[0])
            else NodeGetOrName(Token(token.line, Token.Type.OPERATION, "get-or-name!"), parts[0], static)
        } else {
            val isStatic = static && j == 1
            NodeFMGet(
                Token(token.line, Token.Type.OPERATION, "fget!",),
                parse(token, parts, j, static, clazz),
                parts[j],
                isStatic
            )
        }
    }
}