package ru.DmN.pht.std.ups

import ru.DmN.siberia.Parser
import ru.DmN.siberia.ast.Node
import ru.DmN.siberia.lexer.Token
import ru.DmN.siberia.parser.ctx.ParsingContext
import ru.DmN.pht.std.processors.INodeUniversalProcessor

class NUPNodeAlias(val operation: String) : INodeUniversalProcessor<Node, Node> {
    override fun parse(parser: Parser, ctx: ParsingContext, token: Token): Node? =
        parser.get(ctx, operation)!!.parse(parser, ctx, Token.operation(token.line, operation))
}