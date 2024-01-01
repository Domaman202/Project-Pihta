package ru.DmN.pht.parsers

import ru.DmN.pht.std.ast.NodeFMGet
import ru.DmN.pht.std.node.NodeParsedTypes
import ru.DmN.siberia.Parser
import ru.DmN.siberia.lexer.Token
import ru.DmN.siberia.node.INodeInfo
import ru.DmN.siberia.parser.ctx.ParsingContext
import ru.DmN.siberia.parsers.INodeParser
import ru.DmN.siberia.utils.nextOperation

object NPFGet : INodeParser {
    override fun parse(parser: Parser, ctx: ParsingContext, token: Token): NodeFMGet {
        val static = parser.nextOperation().text!!.toUpperCase() == "STATIC"
        val name = parser.nextOperation().text!!
        return NodeFMGet(INodeInfo.Companion.of(NodeParsedTypes.FGET_B), parser.parseNode(ctx)!!, name, static, true)
    }
}