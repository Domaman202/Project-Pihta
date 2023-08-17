package ru.DmN.pht.base.parser.parsers

import ru.DmN.pht.base.lexer.Token
import ru.DmN.pht.base.Parser
import ru.DmN.pht.base.parser.ast.NodeNodesList

object NPNodesList : SimpleNodeParser<NodeNodesList>() {
    override fun parse(parser: Parser, operationToken: Token): NodeNodesList =
        super.parse(parser, Token(operationToken.line, Token.Type.OPERATION, "progn"))
}