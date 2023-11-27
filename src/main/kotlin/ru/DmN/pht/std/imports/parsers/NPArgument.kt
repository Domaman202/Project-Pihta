package ru.DmN.pht.std.imports.parsers

import ru.DmN.siberia.Parser
import ru.DmN.siberia.lexer.Token
import ru.DmN.siberia.parser.ctx.ParsingContext
import ru.DmN.siberia.ast.Node
import ru.DmN.siberia.parsers.INodeParser
import ru.DmN.pht.std.imports.ast.IValueNode
import ru.DmN.pht.std.imports.ast.NodeArgument

object NPArgument : INodeParser {
    override fun parse(parser: Parser, ctx: ParsingContext, token: Token): Node =
        NodeArgument(token, (parser.parseNode(ctx) as IValueNode).value)
}