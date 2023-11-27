package ru.DmN.pht.std.module.parsers

import ru.DmN.siberia.Parser
import ru.DmN.siberia.lexer.Token
import ru.DmN.siberia.parser.ctx.ParsingContext
import ru.DmN.siberia.ast.Node
import ru.DmN.siberia.parsers.INodeParser
import ru.DmN.pht.std.module.ast.IValueNode
import ru.DmN.pht.std.module.ast.NodeValueList
import ru.DmN.pht.std.ups.NUPValnB

object NPValueList : INodeParser {
    override fun parse(parser: Parser, ctx: ParsingContext, token: Token): Node =
        NUPValnB.parse(parser, ctx) { it -> NodeValueList(token, it.map { (it as IValueNode).value }) }
}