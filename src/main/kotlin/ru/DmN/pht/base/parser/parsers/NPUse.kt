package ru.DmN.pht.base.parser.parsers

import ru.DmN.pht.base.lexer.Token
import ru.DmN.pht.base.Parser
import ru.DmN.pht.base.parser.ast.Node
import ru.DmN.pht.base.parser.ast.NodeUse
import ru.DmN.pht.std.utils.Module

class NPUse : NodeParser() {
    override fun parse(parser: Parser, operationToken: Token): Node {
        val names = ArrayList<String>()
        var tk = parser.nextToken()!!
        while (tk.type == Token.Type.OPERATION) {
            names.add(tk.text!!)
            tk = parser.nextToken()!!
        }
        names.forEach { Module.MODULES[it]!!.inject(parser) }
        return NodeUse(operationToken, names)
    }
}