package ru.DmN.pht.std.parsers

import ru.DmN.pht.base.Parser
import ru.DmN.pht.base.lexer.Token
import ru.DmN.pht.base.parser.ast.Node
import ru.DmN.pht.base.parser.parsers.NodeParser
import ru.DmN.pht.std.ast.NodeFunction

object NPCtor : NodeParser() {
    override fun parse(parser: Parser, operationToken: Token): Node {
        return NPFunction.parse(parser) { args, nodes ->
            NodeFunction(
                Token(
                    operationToken.line,
                    Token.Type.OPERATION,
                    operationToken.text
                ),
                when (operationToken.text) {
                    "ctor", "ector" -> "<init>"
                    "sctor" -> "<cinit>"
                    else -> "<lambda>"
                },
                "void", args, operationToken.text == "sctor", false, false, nodes
            )
        }
    }
}