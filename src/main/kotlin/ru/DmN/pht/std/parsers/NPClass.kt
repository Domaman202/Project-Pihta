package ru.DmN.pht.std.parsers

import ru.DmN.pht.base.Parser
import ru.DmN.pht.base.lexer.Token
import ru.DmN.pht.base.parser.ast.NodeNodesList
import ru.DmN.pht.base.parser.parsers.SimpleNodeParser
import ru.DmN.pht.std.ast.NodeClass
import ru.DmN.pht.base.utils.nextOperation

object NPClass : SimpleNodeParser<NodeNodesList>() {
    override fun parse(parser: Parser, operationToken: Token): NodeNodesList {
        val name = parser.nextOperation().text!!
        val parents = ArrayList<String>()
        var tk = parser.nextToken()!!
        while (tk.type == Token.Type.CLASS) {
            parents.add(tk.text!!)
            tk = parser.nextToken()!!
        }
        if (parents.isEmpty())
            parents += "java.lang.Object"
        parser.tokens.add(tk)
        return super.parse(parser) { NodeClass(operationToken, name, parents, it) }
    }
}