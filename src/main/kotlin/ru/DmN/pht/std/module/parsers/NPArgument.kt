package ru.DmN.pht.std.module.parsers

import ru.DmN.pht.base.Parser
import ru.DmN.pht.base.lexer.Token
import ru.DmN.pht.base.parser.ctx.ParsingContext
import ru.DmN.pht.base.ast.Node
import ru.DmN.pht.base.parsers.INodeParser
import ru.DmN.pht.std.module.ast.IValueNode
import ru.DmN.pht.std.module.ast.NodeArgument

object NPArgument : INodeParser {
    override fun parse(parser: Parser, ctx: ParsingContext, token: Token): Node =
        NodeArgument(token, (parser.parseNode(ctx) as IValueNode).value)
}