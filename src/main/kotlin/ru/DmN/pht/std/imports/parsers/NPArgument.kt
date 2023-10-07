package ru.DmN.pht.std.imports.parsers

import ru.DmN.pht.base.Parser
import ru.DmN.pht.base.lexer.Token
import ru.DmN.pht.base.parser.ParsingContext
import ru.DmN.pht.base.parser.ast.Node
import ru.DmN.pht.base.parser.parsers.INodeParser
import ru.DmN.pht.std.imports.ast.IValueNode
import ru.DmN.pht.std.imports.ast.NodeArgument

object NPArgument : INodeParser {
    override fun parse(parser: Parser, ctx: ParsingContext, operationToken: Token): Node =
        NodeArgument(operationToken, (parser.parseNode(ctx) as IValueNode).value)
}