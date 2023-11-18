package ru.DmN.phtx.pxx.ups

import ru.DmN.pht.base.Parser
import ru.DmN.pht.base.ast.Node
import ru.DmN.pht.base.lexer.Token
import ru.DmN.pht.base.parser.ParsingContext
import ru.DmN.pht.std.processors.INodeUniversalProcessor

class NUPAlias(val operation: String) : INodeUniversalProcessor<Node, Node> {
    override fun parse(parser: Parser, ctx: ParsingContext, token: Token): Node? =
        parser.get(ctx, operation)!!.parse(parser, ctx, Token.operation(token.line, operation))
}