package ru.DmN.pht.base.parser.parsers

import ru.DmN.pht.base.lexer.Token
import ru.DmN.pht.base.Parser
import ru.DmN.pht.base.parser.ast.NodeNodesList

class NPNodesList : SimpleNodeParser<NodeNodesList>() {
    override fun parse(parser: Parser, operationToken: Token): NodeNodesList {
        return super.parse(parser) { NodeNodesList(Token(operationToken.line, Token.Type.OPERATION, "nslist"), it) }
    }
}