package ru.DmN.pht.std.parsers

import ru.DmN.pht.base.Parser
import ru.DmN.pht.base.lexer.Token
import ru.DmN.pht.base.parser.ast.Node
import ru.DmN.pht.base.parser.parsers.NodeParser
import ru.DmN.pht.base.utils.nextCloseCBracket
import ru.DmN.pht.base.utils.nextOpenCBracket
import ru.DmN.pht.std.ast.NodeField
import ru.DmN.pht.base.utils.nextOperation
import ru.DmN.pht.base.utils.nextType
import ru.DmN.pht.std.ast.NodeVar

object NPField : NodeParser() {
    override fun parse(parser: Parser, operationToken: Token): Node {
        val fields = ArrayList<Pair<String, String>>()
        parser.nextOpenCBracket()
        while (true) {
            val tk = parser.nextToken()!!
            when (tk.type) {
                Token.Type.OPEN_CBRACKET -> {
                    fields += Pair(parser.nextOperation().text!!, parser.nextType().text!!)
                    parser.nextCloseCBracket()
                }
                Token.Type.OPERATION -> fields += Pair(tk.text!!, parser.nextType().text!!)
                Token.Type.CLOSE_CBRACKET -> break
                else -> throw RuntimeException()
            }
        }
        return NodeField(operationToken, fields, false)
    }
}