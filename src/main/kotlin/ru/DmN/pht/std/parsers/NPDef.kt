package ru.DmN.pht.std.parsers

import ru.DmN.pht.base.lexer.Token
import ru.DmN.pht.base.Parser
import ru.DmN.pht.base.parser.ast.Node
import ru.DmN.pht.base.parser.parsers.NodeParser
import ru.DmN.pht.base.utils.nextCloseCBracket
import ru.DmN.pht.base.utils.nextOpenCBracket
import ru.DmN.pht.base.utils.nextOperation
import ru.DmN.pht.std.ast.NodeVar

object NPDef : NodeParser() {
    override fun parse(parser: Parser, operationToken: Token): Node {
        val variables = ArrayList<Pair<String, Node?>>()
        parser.nextOpenCBracket()
        while (true) {
            val tk = parser.nextToken()!!
            when (tk.type) {
                Token.Type.OPEN_CBRACKET -> {
                    variables += Pair(parser.nextOperation().text!!, parser.parseNode())
                    parser.nextCloseCBracket()
                }
                Token.Type.OPERATION -> variables += Pair(tk.text!!, parser.parseNode())
                Token.Type.CLOSE_CBRACKET -> break
                else -> throw RuntimeException()
            }
        }
        return NodeVar(operationToken, variables)
    }
}