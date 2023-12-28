package ru.DmN.pht.std.parsers

import ru.DmN.pht.std.ast.NodeMacro
import ru.DmN.pht.std.node.NodeTypes
import ru.DmN.siberia.Parser
import ru.DmN.siberia.ast.Node
import ru.DmN.siberia.lexer.Token
import ru.DmN.siberia.node.INodeInfo
import ru.DmN.siberia.parser.ctx.ParsingContext
import ru.DmN.siberia.parsers.INodeParser
import ru.DmN.siberia.parsers.NPProgn
import ru.DmN.siberia.utils.nextOperation

object NPMacro : INodeParser {
    override fun parse(parser: Parser, ctx: ParsingContext, token: Token): Node {
        val name = parser.nextOperation().text!!
        return NPProgn.parse(parser, ctx) { NodeMacro(INodeInfo.of(NodeTypes.MACRO, ctx, token), it, name) }
    }
}