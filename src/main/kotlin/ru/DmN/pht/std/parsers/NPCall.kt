package ru.DmN.pht.std.parsers

import ru.DmN.pht.base.Parser
import ru.DmN.pht.base.lexer.Token
import ru.DmN.pht.base.parser.ast.NodeNodesList
import ru.DmN.pht.base.parser.parsers.SimpleNodeParser
import ru.DmN.pht.std.ast.NodeFMGet
import ru.DmN.pht.std.ast.NodeMethodCall
import ru.DmN.pht.std.ast.NodeThisCall
import ru.DmN.pht.std.ast.NodeValue

object NPCall : SimpleNodeParser<NodeNodesList>() {
    override fun parse(parser: Parser, operationToken: Token): NodeNodesList =
        super.parse(parser) {
            val method = it.first()
            if (method.tkOperation.text == "mget") {
                method as NodeFMGet
                NodeMethodCall(
                    Token(operationToken.line, operationToken.type, "mcall"),
                    method.name,
                    (listOf(method.instance) + it.drop(1)).toMutableList(),
                    method.static
                )
            } else if (method.isConst() && method is NodeValue && method.vtype == NodeValue.Type.NAMING) {
                NodeThisCall(
                    Token(operationToken.line, operationToken.type, "tcall"),
                    method.value,
                    it.drop(1).toMutableList()
                )
            } else NodeNodesList(operationToken, it)
        }
}