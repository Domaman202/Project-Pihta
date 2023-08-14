package ru.DmN.pht.std.parsers

import ru.DmN.pht.base.Parser
import ru.DmN.pht.base.lexer.Token
import ru.DmN.pht.base.parser.parsers.SimpleNodeParser
import ru.DmN.pht.std.ast.NodeConstructorCall
import ru.DmN.pht.base.utils.nextOperation

object NPCtorCall : SimpleNodeParser<NodeConstructorCall>() {
    override fun parse(parser: Parser, operationToken: Token): NodeConstructorCall {
        val supercall = when (parser.nextOperation().text) {
            "super" -> true
            "this" -> false
            else -> throw RuntimeException()
        }
        return parse(parser) { NodeConstructorCall(operationToken, it, supercall) }
    }
}