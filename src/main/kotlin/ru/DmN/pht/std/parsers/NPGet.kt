package ru.DmN.pht.std.parsers

import ru.DmN.pht.base.lexer.Token
import ru.DmN.pht.base.lexer.isNaming
import ru.DmN.pht.base.lexer.isOperation
import ru.DmN.pht.base.Parser
import ru.DmN.pht.base.parser.ast.Node
import ru.DmN.pht.base.parser.parsers.NodeParser
import ru.DmN.pht.std.ast.NodeFMGet
import ru.DmN.pht.std.ast.NodeGet
import ru.DmN.pht.std.ast.NodeValue

object NPGet : NodeParser() {
    override fun parse(parser: Parser, operationToken: Token): Node {
        val nameToken = parser.nextToken()!!
        return when (nameToken.type) {
            Token.Type.CLASS -> process(operationToken, nameToken.text!!, static = true, klass = true)
            Token.Type.OPERATION -> process(operationToken, nameToken.text!!, static = false, klass = false)
            Token.Type.OPEN_BRACKET -> {
                parser.tokens.push(nameToken)
                return NodeFMGet(
                    operationToken,
                    parser.parseNode()!!,
                    parser.nextToken()!!
                        .let { if (it.isOperation() || it.isNaming()) it else throw RuntimeException() }.text!!,
                    false
                )
            }

            else -> throw RuntimeException()
        }
    }

    private fun process(
        operationToken: Token,
        name: String,
        static: Boolean,
        klass: Boolean
    ): Node {
        val parts = name.split("/", "#") as MutableList<String>
        return process(operationToken, parts, parts.size, static, name.contains("#"), klass)
    }

    private fun process(
        operationToken: Token,
        parts: List<String>,
        i: Int,
        static: Boolean,
        mget: Boolean,
        clazz: Boolean
    ): Node {
        val j = i - 1
        return if (j == 0) {
            if (clazz)
                NodeValue(Token(operationToken.line, operationToken.type, "value"), NodeValue.Type.CLASS, parts[0])
            else NodeGet(operationToken, parts[0], static)
        } else {
            val isStatic = static && j == 1
            NodeFMGet(
                Token(
                    operationToken.line,
                    Token.Type.OPERATION,
                    if (mget && i == parts.size) "mget" else if (isStatic) "sfget" else "fget"
                ),
                process(operationToken, parts, j, static, mget, clazz),
                parts[j],
                isStatic
            )
        }
    }
}