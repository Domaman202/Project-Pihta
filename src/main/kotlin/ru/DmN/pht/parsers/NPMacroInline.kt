package ru.DmN.pht.std.parsers

import ru.DmN.pht.std.ast.NodeMacroUtil
import ru.DmN.pht.std.node.NodeTypes
import ru.DmN.pht.std.parser.utils.macros
import ru.DmN.siberia.Parser
import ru.DmN.siberia.ast.Node
import ru.DmN.siberia.lexer.Token
import ru.DmN.siberia.node.INodeInfo
import ru.DmN.siberia.parser.ctx.ParsingContext
import ru.DmN.siberia.parsers.INodeParser
import ru.DmN.siberia.parsers.NPProgn

object NPMacroInline : INodeParser {
    override fun parse(parser: Parser, ctx: ParsingContext, token: Token): Node =
        NPProgn.parse(parser, ctx) { NodeMacroUtil(INodeInfo.of(NodeTypes.MACRO_INLINE, ctx, token), it, ctx.macros.reversed()) }
}