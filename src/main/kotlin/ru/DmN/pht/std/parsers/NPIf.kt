package ru.DmN.pht.std.parsers

import ru.DmN.pht.base.Parser
import ru.DmN.pht.base.lexer.Token
import ru.DmN.pht.base.parser.ast.Node
import ru.DmN.pht.base.parser.ast.NodeNodesList
import ru.DmN.pht.base.parser.parsers.NodeParser

object NPIf : NodeParser() {
    override fun parse(parser: Parser, operationToken: Token): Node {
        val nodes = ArrayList<Node>()
        nodes.add(parser.parseNode()!!)
        nodes.add(parser.parseNode()!!)
        if (parser.nextToken()!!.apply { parser.tokens.push(this) }.type != Token.Type.CLOSE_BRACKET)
            nodes.add(parser.parseNode()!!)
        return NodeNodesList(operationToken, nodes)
    }
}