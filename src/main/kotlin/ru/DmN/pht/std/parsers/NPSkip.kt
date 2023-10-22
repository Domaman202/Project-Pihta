package ru.DmN.pht.std.parsers

import ru.DmN.pht.base.Parser
import ru.DmN.pht.base.ast.Node
import ru.DmN.pht.base.lexer.Token
import ru.DmN.pht.base.parser.ParsingContext
import ru.DmN.pht.base.parsers.INodeParser
import ru.DmN.pht.base.parsers.NPDefault

object NPSkip : INodeParser { // todo: кривая ересь.
    override fun parse(parser: Parser, ctx: ParsingContext, operationToken: Token): Node? {
        NPDefault.skip(parser, ctx, operationToken)
        return null
    }
}