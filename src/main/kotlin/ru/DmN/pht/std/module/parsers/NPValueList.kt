package ru.DmN.pht.std.module.parsers

import ru.DmN.pht.base.Parser
import ru.DmN.pht.base.lexer.Token
import ru.DmN.pht.base.parser.ctx.ParsingContext
import ru.DmN.pht.base.ast.Node
import ru.DmN.pht.base.parsers.INodeParser
import ru.DmN.pht.std.module.ast.IValueNode
import ru.DmN.pht.std.module.ast.NodeValueList
import ru.DmN.pht.std.ups.NUPValnB

object NPValueList : INodeParser {
    override fun parse(parser: Parser, ctx: ParsingContext, token: Token): Node =
        NUPValnB.parse(parser, ctx) { it -> NodeValueList(token, it.map { (it as IValueNode).value }) }
}