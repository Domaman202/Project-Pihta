package ru.DmN.pht.std.base.parsers

import ru.DmN.pht.base.lexer.Token
import ru.DmN.pht.base.Parser
import ru.DmN.pht.base.parser.ParsingContext
import ru.DmN.pht.base.parser.ast.Node
import ru.DmN.pht.base.parser.parsers.NodeParser
import ru.DmN.pht.std.base.ast.NodeFMGet
import ru.DmN.pht.std.base.ast.NodeFieldSet
import ru.DmN.pht.std.base.ast.NodeGetOrName
import ru.DmN.pht.std.base.ast.NodeSet

object NPSet : NodeParser() {
    override fun parse(parser: Parser, ctx: ParsingContext, operationToken: Token): Node {
        val name = parser.nextToken()!!
        val value = parser.parseNode(ctx)!!
        return when (name.type) {
            Token.Type.CLASS -> process(operationToken, name.text!!, true, value)
            Token.Type.OPERATION -> process(operationToken, name.text!!, false, value)
            else -> throw RuntimeException()
        }
    }

    private fun process(operationToken: Token, name: String, static: Boolean, value: Node): Node {
        val parts = name.split("/")
        return if (parts.size == 1) NodeSet(
            Token(operationToken.line, Token.Type.OPERATION, "set!"),
            parts.last(),
            value
        ) else NodeFieldSet(
            Token(operationToken.line, Token.Type.OPERATION, "fset!"),
            process(operationToken.line, parts, 1, static),
            parts.last(),
            value,
            static
        )
    }

    private fun process(line: Int, parts: List<String>, i: Int, static: Boolean): Node {
        val j = i + 1
        return if (j == parts.size)
            NodeGetOrName(Token(line, Token.Type.OPERATION, "get!"), parts[parts.size - j], static)
        else NodeFMGet(
            Token(line, Token.Type.OPERATION, "fget!"),
            process(line, parts, j, static),
            parts[parts.size - j],
            static
        )
    }
}